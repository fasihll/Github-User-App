package com.example.core.domain.usecase

import com.example.core.data.remote.response.DetailUserResponse
import com.example.core.data.local.entity.FavoriteUserEntity
import com.example.core.data.remote.response.ItemsItem
import com.example.core.data.remote.response.UsersResponse
import com.example.core.domain.model.FavoriteUser
import com.example.core.domain.repository.IUserRepository
import com.example.core.utils.Result
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository): UserUseCase {
    override fun getAllFavoriteUser(): Flow<List<FavoriteUser>> = userRepository
        .getAllFavoriteUser()

    override fun getFavoriteUserByUsername(username: String): Flow<FavoriteUser> =
        userRepository.getFavoriteUserByUsername(username)

    override suspend fun insert(user: FavoriteUser) = userRepository.insert(user)

    override suspend fun delete(user: FavoriteUser) = userRepository.delete(user)

    override fun getThemeSetting(): Flow<Boolean> = userRepository.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) = userRepository
        .saveThemeSetting(isDarkModeActive)

    override fun searchUsers(searchKeyword: String?): Flow<Result<UsersResponse>>  =
        userRepository.searchUsers(searchKeyword)

    override fun showFollowers(uname: String): Flow<Result<List<ItemsItem>>>  =
        userRepository.showFollowers(uname)

    override fun showFollowing(uname: String): Flow<Result<List<ItemsItem>>> = userRepository
        .showFollowing(uname)

    override fun showDetailUser(keyword: String): Flow<Result<DetailUserResponse>>  =
        userRepository.showDetailUser(keyword)
}