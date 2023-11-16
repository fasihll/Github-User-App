package com.example.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.local.datastore.SettingPreferences
import com.example.githubuserapp.data.local.datastore.dataStore
import com.example.githubuserapp.data.remote.response.ItemsItem
import com.example.githubuserapp.databinding.ActivityFavoriteBinding
import com.example.githubuserapp.ui.viewModel.FavoriteViewModel
import com.example.githubuserapp.ui.viewModel.SettingsViewModel
import com.example.githubuserapp.ui.viewModel.SettingsViewModelFactory
import com.example.githubuserapp.ui.viewModel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.rvFavorit.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this@FavoriteActivity,layoutManager.orientation)
        binding.rvFavorit.addItemDecoration(itemDecoration)

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        favoriteViewModel.getAllFavoriteUser().observe(this, { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatar_url, htmlUrl = it
                    .github_url)
                items.add(item)
            }
            setUsersData(items)
        })

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    private fun setUsersData(items: List<ItemsItem?>?) {
        val adapter = UsersAdapter()
        adapter.submitList(items)
        binding.rvFavorit.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}