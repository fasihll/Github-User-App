package com.example.githubuserapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FavoriteUser")
@Parcelize
data class FavoriteUser (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String? = null,

    @ColumnInfo(name = "github_url")
    var github_url: String? = null,

): Parcelable


