package com.example.githubuserapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.domain.model.DetailUserResponse
import com.example.githubuserapp.repository.UserRepository

class DetailUserViewModel(
    private val repository: UserRepository
): ViewModel() {


    private val _listDetailUsers = MutableLiveData<DetailUserResponse>()
    val listDetailUsers: LiveData<DetailUserResponse> = _listDetailUsers

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun showDetailUser(keyword: String) = repository.showDetailUser(_username.value ?: keyword)

}