package com.example.githubuserapp.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.data.local.room.UserDao
import com.example.githubuserapp.data.local.room.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mUserDao.getAllFavoriteUser()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mUserDao
        .getFavoriteUserByUsername(username)

    fun insert(user: FavoriteUser){
        executorService.execute{ mUserDao.insert(user) }
    }

    fun delete(user: FavoriteUser){
        executorService.execute{ mUserDao.delete(user)}
    }

}