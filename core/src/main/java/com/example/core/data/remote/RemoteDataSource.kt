package com.example.core.data.remote

import com.example.core.data.remote.retrofit.ApiServices
import com.example.core.domain.model.DetailUserResponse
import com.example.core.domain.model.ItemsItem
import com.example.core.domain.model.UsersResponse
import com.example.core.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RemoteDataSource(
    private val apiService: ApiServices
) {
    fun searchUsers(searchKeyword: String?): Flow<Result<UsersResponse>> {
        return flow {
            val key = if (searchKeyword.isNullOrEmpty()) {
                "fasih"
            } else {
                searchKeyword.toString()
            }
            emit(Result.Loading)
            try {
                val response = apiService.getUsers(key)
                emit(Result.Success(response))
            }catch (e: HttpException){
                emit(Result.Error(e.message()))
            }
        }

    }

    fun showFollowers(uname: String): Flow<Result<List<ItemsItem>>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = apiService.getFollowers(uname)
                emit(Result.Success(response))
            }catch (e: HttpException){
                emit(Result.Error(e.message()))
            }
        }
    }

    fun showFollowing(uname: String): Flow<Result<List<ItemsItem>>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = apiService.getFollowing(uname)
                emit(Result.Success(response))
            }catch (e: HttpException){
                emit(Result.Error(e.message()))
            }
        }
    }

    fun showDetailUser(keyword: String): Flow<Result<DetailUserResponse>> {
        return  flow {
            emit(Result.Loading)
            try {
                val response = apiService.getUserByUsername(keyword)
                emit(Result.Success(response))
            }catch (e: HttpException){
                emit(Result.Error(e.message()))
            }
        }
    }
}