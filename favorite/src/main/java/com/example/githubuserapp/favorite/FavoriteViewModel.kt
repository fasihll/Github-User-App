package com.example.githubuserapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.data.local.entity.FavoriteUserEntity
import com.example.core.domain.model.FavoriteUser
import com.example.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val userUseCase: UserUseCase
): ViewModel() {

    fun getAllFavoriteUser() = userUseCase.getAllFavoriteUser().asLiveData()

    fun getFavoriteUserByUsername(username: String) =
        userUseCase.getFavoriteUserByUsername(username).asLiveData()

    fun insert(user: FavoriteUser) {
        viewModelScope.launch {
            userUseCase.insert(user)
        }
    }

    fun delete(user: FavoriteUser){
        viewModelScope.launch {
            userUseCase.delete(user)
        }
    }
}