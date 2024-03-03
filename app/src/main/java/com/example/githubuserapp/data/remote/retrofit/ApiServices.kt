package com.example.githubuserapp.data.remote.retrofit

import com.example.githubuserapp.domain.model.DetailUserResponse
import com.example.githubuserapp.domain.model.ItemsItem
import com.example.githubuserapp.domain.model.UsersResponse
import com.example.githubuserapp.repository.UserRepository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") q:String
    ): UsersResponse

    @GET("users/{username}")
    suspend fun getUserByUsername(
        @Path("username") username:String
    ): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username:String
    ): List<ItemsItem>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username:String
    ): List<ItemsItem>
}