package com.example.core.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.domain.model.FavoriteUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: FavoriteUser)

    @Update
    fun update(user: FavoriteUser)

    @Delete
    suspend fun delete(user: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser ORDER BY id ASC")
    fun getAllFavoriteUser(): Flow<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): Flow<FavoriteUser>
}