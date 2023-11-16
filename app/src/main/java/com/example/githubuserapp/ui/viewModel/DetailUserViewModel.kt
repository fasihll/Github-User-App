package com.example.githubuserapp.ui.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.ui.DetailUserActivity
import com.example.githubuserapp.data.remote.response.DetailUserResponse
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {


    private val _listDetailUsers = MutableLiveData<DetailUserResponse>()
    val listDetailUsers: LiveData<DetailUserResponse> = _listDetailUsers

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun showDetailUser(keyword: String){

        val client = ApiConfig.getApiServices().getUserByUsername(keyword)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listDetailUsers.value = response.body()
                }else{
                    Log.e(DetailUserActivity.TAG,response.message())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailUserActivity.TAG,"${t.message}")
            }
        })
    }

    fun setUsername(key: String){
        _username.value = key
        showDetailUser(key)
    }
}