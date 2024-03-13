package com.example.githubuserapp.di

import com.example.core.domain.usecase.UserInteractor
import com.example.core.domain.usecase.UserUseCase
import com.example.githubuserapp.MainViewModel
import com.example.githubuserapp.detail.DetailUserViewModel
import com.example.githubuserapp.detail.FavoriteViewModel
import com.example.githubuserapp.follow.FollowViewModel
import com.example.githubuserapp.setting.SettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { FollowViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailUserViewModel(get()) }
}