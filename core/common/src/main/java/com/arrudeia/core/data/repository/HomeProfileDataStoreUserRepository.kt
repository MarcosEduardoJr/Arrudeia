package com.arrudeia.core.data.repository

import com.arrudeia.core.data.repository.entity.ProfileDataStoreUserRepositoryEntity
import com.arrudeia.core.result.Result

interface HomeProfileDataStoreUserRepository {
    suspend fun getUserData(): Result<ProfileDataStoreUserRepositoryEntity?>

     suspend fun getUuid(): String?


}