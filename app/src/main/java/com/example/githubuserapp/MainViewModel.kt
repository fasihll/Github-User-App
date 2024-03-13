package com.example.githubuserapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.UserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel(
    val userUseCase: UserUseCase
): ViewModel() {
    val searchKeyword = MutableStateFlow("")

    companion object{
        private const val TAG = "MainViewModel"
    }

    suspend fun showUsers() = userUseCase.searchUsers(searchKeyword.value).asLiveData()

    suspend fun searchUsers(key: String){
            searchKeyword.value = key
            showUsers()
    }


}