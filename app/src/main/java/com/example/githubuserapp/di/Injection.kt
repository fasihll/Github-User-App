package com.example.githubuserapp.di

import android.content.Context
import com.example.githubuserapp.data.local.datastore.SettingPreferences
import com.example.githubuserapp.data.local.datastore.dataStore
import com.example.githubuserapp.data.local.room.UserRoomDatabase
import com.example.githubuserapp.repository.UserRepository
import com.example.githubuserapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = SettingPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiServices()
        val database = UserRoomDatabase.getDatabase(context)
        return UserRepository(pref,apiService,database)
    }
}