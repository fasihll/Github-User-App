package com.example.core.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.local.entity.FavoriteUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: FavoriteUserEntity)

    @Update
    fun update(user: FavoriteUserEntity)

    @Delete
    suspend fun delete(user: FavoriteUserEntity)

    @Query("SELECT * FROM FavoriteUser ORDER BY id ASC")
    fun getAllFavoriteUser(): Flow<List<FavoriteUserEntity>>

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): Flow<FavoriteUserEntity>
}