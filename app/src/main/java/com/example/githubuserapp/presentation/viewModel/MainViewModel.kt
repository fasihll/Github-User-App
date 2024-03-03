package com.example.githubuserapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.domain.model.ItemsItem
import com.example.githubuserapp.repository.UserRepository
import kotlinx.coroutines.launch


class MainViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    private val _listUsers = MutableLiveData<List<ItemsItem?>?>()
    val listUsers: LiveData<List<ItemsItem?>?> = _listUsers

    private val _searchKeyword = MutableLiveData<String>()
    val searchKeyword: LiveData<String> = _searchKeyword

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        showUsers()
    }

    fun showUsers() = userRepository.searchUsers(searchKeyword.value)

    fun searchUsers(key: String){
        _searchKeyword.value = key
        showUsers()
    }


}