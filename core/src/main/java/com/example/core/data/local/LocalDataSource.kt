package com.example.core.data.local

import com.example.core.data.local.datastore.SettingPreferences
import com.example.core.data.local.room.UserRoomDatabase
import com.example.core.domain.model.FavoriteUser
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val userPreferences: SettingPreferences,
    private val database: UserRoomDatabase
) {

    fun getThemeSetting(): Flow<Boolean> {
        return userPreferences.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        userPreferences.saveThemeSetting(isDarkModeActive)
    }

    fun getAllFavoriteUser(): Flow<List<FavoriteUser>> = database.userDao()
        .getAllFavoriteUser()

    fun getFavoriteUserByUsername(username: String): Flow<FavoriteUser> = database
        .userDao().getFavoriteUserByUsername(username)

    suspend fun insert(user: FavoriteUser){
        database.userDao().insert(user)
    }

    suspend fun delete(user: FavoriteUser){
        database.userDao().delete(user)
    }
}