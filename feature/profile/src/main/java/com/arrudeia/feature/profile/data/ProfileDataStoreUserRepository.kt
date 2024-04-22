package com.arrudeia.feature.profile.data

import com.arrudeia.feature.profile.data.entity.ProfileDataStoreUserRepositoryEntity


interface ProfileDataStoreUserRepository {


    suspend fun saveUser(
        user: ProfileDataStoreUserRepositoryEntity
    ): Boolean

    suspend fun getUserData():  ProfileDataStoreUserRepositoryEntity?

    suspend fun logoutUser(
    )
}