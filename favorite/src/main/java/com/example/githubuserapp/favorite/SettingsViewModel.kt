package com.example.githubuserapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(private val userUseCase: UserUseCase): ViewModel() {
    val getThemeSetting = userUseCase.getThemeSetting().asLiveData()

    fun saveThemeSetting(isChecked: Boolean) {
        viewModelScope.launch {
            userUseCase.saveThemeSetting(isChecked)
        }
    }

}