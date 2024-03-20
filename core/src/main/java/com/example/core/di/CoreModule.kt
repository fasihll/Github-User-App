package com.example.core.di

import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.data.local.LocalDataSource
import com.example.core.data.local.datastore.SettingPreferences
import com.example.core.data.local.datastore.dataStore
import com.example.core.data.local.room.UserRoomDatabase
import com.example.core.data.remote.RemoteDataSource
import com.example.core.data.remote.retrofit.ApiServices
import com.example.core.domain.repository.IUserRepository
import com.example.core.data.UserRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val authInterceptor = Interceptor{chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization","token ${BuildConfig.HEADER}")
                .build()
            chain.proceed(requestHeaders)
        }

        val loggingInterceptor = if (BuildConfig.DEBUG){
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }else{
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val hostname = "api.github.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/jFaeVpA8UQuidlJkkpIdq3MPwD0m8XbuCRbJlezysBE=")
            .add(hostname, "sha256/Wec45nQiFwKvHtuHxSAMGkt19k+uPSw9JlEkxhvYPHk=")
            .add(hostname, "sha256/lmo8/KPXoMsxI+J9hY+ibNm2r0IYChmOsF9BxD74PVc=")
            .add(hostname, "sha256/6YBE8kK4d5J1qu1wEjyoKqzEIvyRY5HyM/NB2wKdcZo=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiServices::class.java)
    }

}

val databaseModule = module {
    factory { get<UserRoomDatabase>().userDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("fasih123".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            UserRoomDatabase::class.java,"user_database.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val dataStoreModule = module {
    single { androidContext().dataStore }
    single { SettingPreferences(get()) }
}


val repositoryModule = module {
    single { LocalDataSource(get(),get()) }
    single { RemoteDataSource(get()) }
    single<IUserRepository> { UserRepository(get(), get()) }
}
