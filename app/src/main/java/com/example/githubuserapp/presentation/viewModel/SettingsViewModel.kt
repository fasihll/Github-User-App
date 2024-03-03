package com.example.githubuserapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.data.local.datastore.SettingPreferences
import com.example.githubuserapp.repository.UserRepository
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: UserRepository): ViewModel() {
    val getThemeSetting = repository.getThemeSetting()

    fun saveThemeSetting(isChecked: Boolean) {
        viewModelScope.launch {
            repository.saveThemeSetting(isChecked)
        }
    }

}