package com.example.core.data

import com.example.core.data.local.LocalDataSource
import com.example.core.data.remote.RemoteDataSource
import com.example.core.domain.model.DetailUserResponse
import com.example.core.domain.model.FavoriteUser
import com.example.core.domain.model.ItemsItem
import com.example.core.domain.model.UsersResponse
import com.example.core.domain.repository.IUserRepository
import com.example.core.utils.Result
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): IUserRepository {


    override fun getAllFavoriteUser(): Flow<List<FavoriteUser>> =
        localDataSource.getAllFavoriteUser()

    override fun getFavoriteUserByUsername(username: String): Flow<FavoriteUser> =
        localDataSource.getFavoriteUserByUsername(username)

    override suspend fun insert(user: FavoriteUser){
        localDataSource.insert(user)
    }

    override suspend fun delete(user: FavoriteUser){
        localDataSource.delete(user)
    }

    override fun getThemeSetting(): Flow<Boolean>{
        return localDataSource.getThemeSetting()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        localDataSource.saveThemeSetting(isDarkModeActive)
    }


    override fun searchUsers(searchKeyword: String?): Flow<Result<UsersResponse>> =
        remoteDataSource.searchUsers(searchKeyword)

    override fun showFollowers(uname: String): Flow<Result<List<ItemsItem>>> = remoteDataSource
        .showFollowers(uname)

    override fun showFollowing(uname: String): Flow<Result<List<ItemsItem>>> = remoteDataSource
        .showFollowing(uname)

    override fun showDetailUser(keyword: String): Flow<Result<DetailUserResponse>> =
        remoteDataSource.showDetailUser(keyword)

}