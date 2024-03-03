package com.example.githubuserapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.repository.UserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: UserRepository
): ViewModel() {

    fun getAllFavoriteUser() = repository.getAllFavoriteUser()

    fun getFavoriteUserByUsername(username: String) = repository.getFavoriteUserByUsername(username)

    fun insert(user: FavoriteUser) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }

    fun delete(user: FavoriteUser){
        viewModelScope.launch {
            repository.delete(user)
        }
    }
}