package com.arrudeia.feature.home.data

import com.arrudeia.feature.home.data.entity.ProfileDataStoreUserRepositoryEntity


interface HomeProfileDataStoreUserRepository {


    suspend fun saveUser(
        user: ProfileDataStoreUserRepositoryEntity
    ): Boolean

    suspend fun getUserData():  ProfileDataStoreUserRepositoryEntity?

    suspend fun logoutUser(
    )
}