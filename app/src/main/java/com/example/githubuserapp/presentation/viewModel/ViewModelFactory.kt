package com.example.githubuserapp.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.githubuserapp.di.Injection
import com.example.githubuserapp.repository.UserRepository

//class SettingsViewModelFactory(private val pref: com.example.githubuserapp.data.local.datastore.SettingPreferences): NewInstanceFactory() {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
//            return SettingsViewModel(pref) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//    }
//}

class ViewModelFactory(
    private val userRepository: UserRepository,
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(FollowViewModel::class.java) -> {
                FollowViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(DetailUserViewModel::class.java) -> {
                DetailUserViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknow ViewModel Class: "+ modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context): ViewModelFactory {
            return ViewModelFactory(Injection.provideUserRepository(context))
        }
    }

}