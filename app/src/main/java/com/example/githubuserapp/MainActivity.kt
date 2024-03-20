package com.example.githubuserapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.model.ItemsItem
import com.example.core.utils.Result
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.setting.SettingActivity
import com.example.githubuserapp.setting.SettingsViewModel
import com.example.githubuserapp.ui.UsersAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by  viewModel()
    private val viewModel2: SettingsViewModel by viewModel()
    companion object{
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            getUsers()
        }

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    lifecycleScope.launch {
                        viewModel.searchUsers(searchView.text.toString())
                        getUsers()
                    }
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
                R.id.menu2 -> {
                    val uri = Uri.parse("githubuserapp://favorite")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.data = uri  // Optional: Pass data to the activity
                    startActivity(intent)
                }
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

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private suspend fun getUsers(){
        viewModel.showUsers().observe(this, Observer { result ->
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
        })
    }
}