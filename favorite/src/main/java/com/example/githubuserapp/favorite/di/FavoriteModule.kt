package com.example.githubuserapp.favorite.di

import com.example.githubuserapp.favorite.FavoriteViewModel
import com.example.githubuserapp.favorite.SettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}