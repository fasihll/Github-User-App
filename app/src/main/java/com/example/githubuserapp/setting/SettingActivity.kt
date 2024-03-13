package com.example.githubuserapp.setting

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.githubuserapp.databinding.ActivitySettingBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val viewModel: SettingsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        viewModel.getThemeSetting.observe(this){ result ->
            Log.d("Status =>",result.toString())
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