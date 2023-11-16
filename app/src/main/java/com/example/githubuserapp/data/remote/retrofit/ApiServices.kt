package com.example.githubuserapp.data.remote.retrofit

import com.example.githubuserapp.data.remote.response.DetailUserResponse
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.data.remote.response.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("search/users")
    fun getUsers(
        @Query("q") q:String
    ): Call<UsersResponse>

    @GET("users/{username}")
    fun getUserByUsername(
        @Path("username") username:String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username:String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username:String
    ): Call<List<ItemsItem>>
}