package com.example.githubuserapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.data.remote.response.DetailUserResponse
import com.example.core.domain.usecase.UserUseCase

class DetailUserViewModel(
    private val userUseCase: UserUseCase
): ViewModel() {


    private val _listDetailUsers = MutableLiveData<DetailUserResponse>()
    val listDetailUsers: LiveData<DetailUserResponse> = _listDetailUsers

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun showDetailUser(keyword: String) = userUseCase.showDetailUser(_username.value ?: keyword).asLiveData()

}