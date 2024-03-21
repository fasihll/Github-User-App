package com.example.core.domain.usecase

import com.example.core.data.remote.response.DetailUserResponse
import com.example.core.data.local.entity.FavoriteUserEntity
import com.example.core.data.remote.response.ItemsItem
import com.example.core.data.remote.response.UsersResponse
import com.example.core.domain.model.FavoriteUser
import com.example.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
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