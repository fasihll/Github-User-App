package com.example.core.data

import com.example.core.data.local.LocalDataSource
import com.example.core.data.remote.RemoteDataSource
import com.example.core.data.remote.response.DetailUserResponse
import com.example.core.data.local.entity.FavoriteUserEntity
import com.example.core.data.remote.response.ItemsItem
import com.example.core.data.remote.response.UsersResponse
import com.example.core.domain.model.FavoriteUser
import com.example.core.domain.repository.IUserRepository
import com.example.core.utils.DataMapper
import com.example.core.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): IUserRepository {


    override fun getAllFavoriteUser(): Flow<List<FavoriteUser>>{
        return localDataSource.getAllFavoriteUser().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoriteUserByUsername(username: String): Flow<FavoriteUser>{
        return localDataSource.getFavoriteUserByUsername(username).map {
            if (it != null) {
                DataMapper.mapOneEntitiesToDomain(it)
            } else {
                // Handle the case when entity is null, such as by throwing an exception
                DataMapper.mapOneEntitiesToDomain(FavoriteUserEntity(
                    username = null,
                    avatar_url = null,
                    github_url = null
                ))
            }
        }
    }


    override suspend fun insert(user: FavoriteUser){
        val entity = DataMapper.mapDomainToEntity(user)
        localDataSource.insert(entity)
    }

    override suspend fun delete(user: FavoriteUser){
        val entity = DataMapper.mapDomainToEntity(user)
        localDataSource.delete(entity)
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