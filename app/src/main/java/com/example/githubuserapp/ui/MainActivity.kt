package com.example.githubuserapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.datastore.SettingPreferences
import com.example.githubuserapp.data.local.datastore.dataStore
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.ui.viewModel.MainViewModel
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.ui.viewModel.SettingsViewModel
import com.example.githubuserapp.ui.viewModel.SettingsViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val  mainViewModel by viewModels<MainViewModel>()
    companion object{
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.searchUsers(searchView.text.toString())
                    false
                }

            val layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@MainActivity,layoutManager.orientation)
            rvUsers.addItemDecoration(itemDecoration)

        }

        mainViewModel.listUsers.observe(this,{listUsers ->
            setUsersData(listUsers)
        })

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu1 -> startActivity(Intent(this,SettingActivity::class.java))
                R.id.menu2 -> startActivity(Intent(this,FavoriteActivity::class.java))
            }
            true
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )

        settingsViewModel.getThemeSetting().observe(this){isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
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
}