package com.example.core.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteUser (
    val id: Long = 0,
    var username: String? = null,
    var avatar_url: String? = null,
    var github_url: String? = null,
): Parcelable

