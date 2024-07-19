package com.arrudeia.feature.services.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.entity.ProfileDataStoreUserRepositoryEntity

interface HomeProfileDataStoreUserRepository {
    suspend fun getUserData(): Result<ProfileDataStoreUserRepositoryEntity?>

}