package com.arrudeia.core.data.repository

import com.arrudeia.core.data.interactions.Result
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface DataStoreUserRepository {


    suspend fun saveUser(
        user: DataStoreUserRepositoryEntity
    ) : Boolean

    suspend fun getUserData(): DataStoreUserRepositoryEntity?
}