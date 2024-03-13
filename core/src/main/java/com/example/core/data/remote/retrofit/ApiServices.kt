package com.example.core.data.remote.retrofit

import com.example.core.domain.model.DetailUserResponse
import com.example.core.domain.model.ItemsItem
import com.example.core.domain.model.UsersResponse
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