package com.arrudeia.core.data.repository

import com.arrudeia.core.data.repository.entity.ProfileDataStoreUserRepositoryEntity


interface ProfileDataStoreUserRepository {


    suspend fun saveUser(
        user: ProfileDataStoreUserRepositoryEntity
    ): Boolean

    suspend fun getUserData():  ProfileDataStoreUserRepositoryEntity?

    suspend fun logoutUser(
    )


    suspend fun getUuid(): String

    suspend fun getImageUser(): String
}