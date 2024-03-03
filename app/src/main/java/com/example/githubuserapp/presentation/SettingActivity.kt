package com.example.githubuserapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.data.local.datastore.SettingPreferences
import com.example.githubuserapp.data.local.datastore.dataStore
import com.example.githubuserapp.databinding.ActivitySettingBinding
import com.example.githubuserapp.presentation.viewModel.MainViewModel
import com.example.githubuserapp.presentation.viewModel.SettingsViewModel
import com.example.githubuserapp.presentation.viewModel.ViewModelFactory
import com.example.githubuserapp.utils.Result

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingsViewModel>{
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        viewModel.getThemeSetting.observe(this){ result ->
            if (result){
               AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
               binding.swtichTheme.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.swtichTheme.isChecked = false
            }
        }

        binding.swtichTheme.setOnCheckedChangeListener{_:CompoundButton?, isChacked: Boolean ->
            viewModel.saveThemeSetting(isChacked)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
}