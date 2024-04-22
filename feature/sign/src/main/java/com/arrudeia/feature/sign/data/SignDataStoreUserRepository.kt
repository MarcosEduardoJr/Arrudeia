package com.arrudeia.feature.sign.data

import com.arrudeia.feature.sign.data.entity.SignDataStoreUserRepositoryEntity


interface SignDataStoreUserRepository {


    suspend fun saveUser(
        user: SignDataStoreUserRepositoryEntity
    ): Boolean


}