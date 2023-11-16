package com.example.githubuserapp.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.data.remote.response.DetailUserResponse
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.example.githubuserapp.ui.viewModel.DetailUserViewModel
import com.example.githubuserapp.ui.viewModel.FavoriteViewModel
import com.example.githubuserapp.ui.viewModel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()
    private lateinit var favoriteViewModel: FavoriteViewModel

    private var UserNow: FavoriteUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteViewModel = obtainViewModel(this@DetailUserActivity)

        val name = intent.getStringExtra(USERNAME)
        val avatar = intent.getStringExtra(AVATAR_URL)
        val github_url = intent.getStringExtra(GITHUB_URL)

        Glide.with(this@DetailUserActivity)
            .load(avatar)
            .into(binding.profileImage)
        binding.name.text = "Unknow"
        binding.username.text = name
        binding.followers.text = "0 Followers"
        binding.following.text = "0 Following"


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = name.toString()
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailUserViewModel.setUsername(name.toString())
        detailUserViewModel.listDetailUsers.observe(this,{  listDetailUser ->
            setData(listDetailUser)
        })

        detailUserViewModel.isLoading.observe(this){
            showLoading(it)
        }

        showLoading(true)

        favoriteViewModel.getFavoriteUserByUsername(name.toString()).observe(this,{res ->
            if (res != null){
                UserNow = res
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(binding.fabFavorite
                    .context, R
                    .drawable
                    .baseline_favorite_24))
            }else{
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(binding.fabFavorite
                    .context, R
                    .drawable
                    .baseline_favorite_border_24))
            }
        })

        binding.fabFavorite.setOnClickListener{
            if (UserNow == null){
                val data = FavoriteUser(
                    0,
                    name,
                    avatar,
                    github_url
                )
                favoriteViewModel.insert(data)
                Toast.makeText(this@DetailUserActivity,"Ditambahakn di Favorite",Toast
                    .LENGTH_SHORT).show()
            }else{
                favoriteViewModel.delete(UserNow!!)
                UserNow = null
                Toast.makeText(this@DetailUserActivity,"Dihapus di Favorite",Toast
                    .LENGTH_SHORT).show()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    private fun setData(detailUsers: DetailUserResponse){
        Glide.with(this@DetailUserActivity)
            .load(detailUsers?.avatarUrl)
            .into(binding.profileImage)
        binding.name.text = detailUsers?.name
        binding.username.text = detailUsers?.login
        binding.followers.text = "${detailUsers?.followers} Followers"
        binding.following.text = "${detailUsers?.following} Following"
    }



    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    companion object{
        const val TAG = "DetailUserActivity"
        const val USERNAME = "username"
        const val AVATAR_URL = "avatar_url"
        const val GITHUB_URL = "github_url"
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}