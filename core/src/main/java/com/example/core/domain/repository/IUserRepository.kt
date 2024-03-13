package com.example.core.domain.repository

import com.example.core.domain.model.DetailUserResponse
import com.example.core.domain.model.FavoriteUser
import com.example.core.domain.model.ItemsItem
import com.example.core.domain.model.UsersResponse
import com.example.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getAllFavoriteUser(): Flow<List<FavoriteUser>>

    fun getFavoriteUserByUsername(username: String): Flow<FavoriteUser>

    suspend fun insert(user: FavoriteUser)

    suspend fun delete(user: FavoriteUser)

    fun getThemeSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)

    fun searchUsers(searchKeyword: String?): Flow<Result<UsersResponse>>

    fun showFollowers(uname: String): Flow<Result<List<ItemsItem>>>
    fun showFollowing(uname: String): Flow<Result<List<ItemsItem>>>
    fun showDetailUser(keyword: String): Flow<Result<DetailUserResponse>>
}