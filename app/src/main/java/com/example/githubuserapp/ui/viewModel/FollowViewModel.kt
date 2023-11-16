package com.example.githubuserapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.ui.DetailUserActivity
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    val listFollowers: LiveData<List<ItemsItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

     fun showFollowers(uname: String){
        val client = ApiConfig.getApiServices().getFollowers(uname)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listFollowers.value = response.body()
                }else{
                    Log.e(DetailUserActivity.TAG,response.message())
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailUserActivity.TAG,"${t.message}")
            }
        })
    }

     fun showFollowing(uname: String){
        val client = ApiConfig.getApiServices().getFollowing(uname)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listFollowing.value = response.body()
                }else{
                    Log.e(DetailUserActivity.TAG,response.message())
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailUserActivity.TAG,"${t.message}")
            }
        })
    }
}