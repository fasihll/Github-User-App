package com.example.githubuserapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.data.remote.response.UsersResponse
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
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

    private fun showUsers(){
        val q = searchKeyword.value ?: "fasih"
        _isLoading.value = true
        val client = ApiConfig.getApiServices().getUsers(q)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _listUsers.value = responseBody.items!!
                    }
                }else{
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"onFailure: ${t.message}")
            }
        })
    }

    fun searchUsers(q: String){
        _searchKeyword.value = q
        showUsers()
    }

}