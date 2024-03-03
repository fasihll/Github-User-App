package com.example.githubuserapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.datastore.SettingPreferences
import com.example.githubuserapp.data.local.datastore.dataStore
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.domain.model.ItemsItem
import com.example.githubuserapp.presentation.viewModel.MainViewModel
import com.example.githubuserapp.presentation.viewModel.SettingsViewModel
import com.example.githubuserapp.presentation.viewModel.ViewModelFactory
import com.example.githubuserapp.utils.Result

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>{
        ViewModelFactory.getInstance(this)
    }
    private val viewModel2 by viewModels<SettingsViewModel>{
        ViewModelFactory.getInstance(this)
    }
    companion object{
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUsers()

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    viewModel.searchUsers(searchView.text.toString())
                    getUsers()
                    false
                }

            val layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@MainActivity,layoutManager.orientation)
            rvUsers.addItemDecoration(itemDecoration)

        }



        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu1 -> startActivity(Intent(this, SettingActivity::class.java))
                R.id.menu2 -> startActivity(Intent(this, FavoriteActivity::class.java))
            }
            true
        }



        viewModel2.getThemeSetting.observe(this) { result ->
            if (result) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }
    private fun setUsersData(items: List<ItemsItem?>?) {
        val adapter = UsersAdapter()
        adapter.submitList(items)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getUsers(){
        viewModel.showUsers().observe(this){ result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        setUsersData(result.data.items)
                    }
                    is Result.Error -> {
                        showLoading(true)
                        Log.d("RES", "Error!!")
                    }
                }
            } else {
                Toast.makeText(this,"Data Gaga Di Muat", Toast.LENGTH_SHORT).show()
            }
        }
    }
}