package com.example.githubuserapp.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.data.remote.response.ItemsItem
import com.example.core.domain.usecase.UserUseCase

class FollowViewModel(
    private val userUseCase: UserUseCase
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    val listFollowers: LiveData<List<ItemsItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

     fun showFollowers(uname: String) = userUseCase.showFollowers(uname).asLiveData()

     fun showFollowing(uname: String) = userUseCase.showFollowing(uname).asLiveData()
}