package com.arrudeia.core.data.repository

import com.arrudeia.core.data.repository.entity.ProfileDataStoreUserRepositoryEntity
import com.arrudeia.core.result.Result

interface UserDataStoreUserRepository {
    suspend fun getUserData(): Result<ProfileDataStoreUserRepositoryEntity?>

}