package com.example.core.utils

import com.example.core.data.local.entity.FavoriteUserEntity
import com.example.core.domain.model.FavoriteUser

object DataMapper {
    fun mapResponsesToEntities(input: List<FavoriteUser>): List<FavoriteUserEntity> {
        val favoriteList = ArrayList<FavoriteUserEntity>()
        input.map {
            val favorite = FavoriteUserEntity(
                id = it.id,
                username = it.username,
                avatar_url = it.avatar_url,
                github_url = it.github_url
            )
            favoriteList.add(favorite)
        }
        return favoriteList
    }

    fun mapEntitiesToDomain(input: List<FavoriteUserEntity>): List<FavoriteUser> =
        input.map {
            FavoriteUser(
                id = it.id,
                username = it.username,
                avatar_url = it.avatar_url,
                github_url = it.github_url
            )
        }
    fun mapOneEntitiesToDomain(input: FavoriteUserEntity): FavoriteUser =
        FavoriteUser(
            id = input.id,
            username = input?.username,
            avatar_url = input?.avatar_url,
            github_url = input?.github_url
        )



    fun mapDomainToEntity(input: FavoriteUser) = FavoriteUserEntity(
        id = input.id,
        username = input.username,
        avatar_url = input.avatar_url,
        github_url = input.github_url
    )
}