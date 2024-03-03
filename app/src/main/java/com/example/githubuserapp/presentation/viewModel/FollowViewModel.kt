package com.example.githubuserapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.domain.model.ItemsItem
import com.example.githubuserapp.repository.UserRepository

class FollowViewModel(
    private val repository: UserRepository
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    val listFollowers: LiveData<List<ItemsItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

     fun showFollowers(uname: String) = repository.showFollowers(uname)

     fun showFollowing(uname: String) = repository.showFollowing(uname)
}