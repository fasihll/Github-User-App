package com.example.githubuserapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.data.local.datastore.SettingPreferences
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.data.local.room.UserDao
import com.example.githubuserapp.data.local.room.UserRoomDatabase
import com.example.githubuserapp.data.remote.retrofit.ApiServices
import com.example.githubuserapp.domain.model.DetailUserResponse
import com.example.githubuserapp.domain.model.ItemsItem
import com.example.githubuserapp.domain.model.UsersResponse
import com.example.githubuserapp.presentation.viewModel.MainViewModel
import com.example.githubuserapp.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(
    private val userPreference: SettingPreferences,
    private val apiService: ApiServices,
    private val database: UserRoomDatabase
) {


    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = database.userDao().getAllFavoriteUser()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = database.userDao()
        .getFavoriteUserByUsername(username)

    suspend fun insert(user: FavoriteUser){
        database.userDao().insert(user)
    }

    suspend fun delete(user: FavoriteUser){
        database.userDao().delete(user)
    }

    fun getThemeSetting(): LiveData<Boolean>{
        return userPreference.getThemeSetting().asLiveData()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean){
        userPreference.saveThemeSetting(isDarkModeActive)
    }


     fun searchUsers(searchKeyword: String?): LiveData<Result<UsersResponse>> = liveData{
         var key =""

         if (searchKeyword == null || searchKeyword == ""){
             key = "fasih"
         }else{
             key = searchKeyword.toString()
         }
        emit(Result.Loading)
        try {
            val response = apiService.getUsers(key)
            emit(Result.Success(response))
        }catch (e: HttpException){
            emit(Result.Error(e.message()))
        }

    }

    fun showFollowers(uname: String): LiveData<Result<List<ItemsItem>>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.getFollowers(uname)
            emit(Result.Success(response))
        }catch (e: HttpException){
            emit(Result.Error(e.message()))
        }
    }

    fun showFollowing(uname: String): LiveData<Result<List<ItemsItem>>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.getFollowing(uname)
            emit(Result.Success(response))
        }catch (e: HttpException){
            emit(Result.Error(e.message()))
        }
    }

    fun showDetailUser(keyword: String): LiveData<Result<DetailUserResponse>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.getUserByUsername(keyword)
            emit(Result.Success(response))
        }catch (e: HttpException){
            emit(Result.Error(e.message()))
        }
    }

}