package com.arrudeia.core.data.repository

import com.arrudeia.core.data.entity.DataStoreUserRepositoryEntity

interface DataStoreUserRepository {


    suspend fun saveUser(
        user: DataStoreUserRepositoryEntity
    ): Boolean

    suspend fun getUserData(): DataStoreUserRepositoryEntity?

    suspend fun logoutUser(
    )
}