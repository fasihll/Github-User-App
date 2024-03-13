package com.example.githubuserapp.favorite

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.model.ItemsItem
import com.example.githubuserapp.favorite.databinding.ActivityFavoriteBinding
import com.example.githubuserapp.favorite.di.favoriteModule
import com.example.githubuserapp.ui.UsersAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel : FavoriteViewModel by  viewModel()
    private val viewModel1: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.rvFavorit.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this@FavoriteActivity,layoutManager.orientation)
        binding.rvFavorit.addItemDecoration(itemDecoration)



        viewModel.getAllFavoriteUser().observe(this, { result ->
            if (result != null){
                        val items = arrayListOf<ItemsItem>()
                        result.map {
                            val item = ItemsItem(login = it.username, avatarUrl = it.avatar_url, htmlUrl = it
                                .github_url)
                            items.add(item)
                        }
                        setUsersData(items)
            }else{
                Toast.makeText(this,"Data Gaga Di Muat",Toast.LENGTH_SHORT).show()
            }
        })


        viewModel1.getThemeSetting.observe(this){ result ->

            if (result){
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

}