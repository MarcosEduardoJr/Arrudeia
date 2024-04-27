package com.arrudeia.feature.home.data

import com.arrudeia.feature.home.data.entity.ProfileDataStoreUserRepositoryEntity

import com.arrudeia.core.result.Result

interface HomeProfileDataStoreUserRepository {
    suspend fun getUserData(): Result<ProfileDataStoreUserRepositoryEntity?>

}