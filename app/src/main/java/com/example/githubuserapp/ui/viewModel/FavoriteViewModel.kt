package com.example.githubuserapp.ui.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.data.local.repository.UserRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mUserRepository.getAllFavoriteUser()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mUserRepository
        .getFavoriteUserByUsername(username)

    fun insert(user: FavoriteUser) = mUserRepository.insert(user)

    fun delete(user: FavoriteUser) = mUserRepository.delete(user)
}