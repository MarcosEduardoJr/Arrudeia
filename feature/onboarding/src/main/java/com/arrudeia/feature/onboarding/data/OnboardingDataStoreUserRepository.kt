package com.arrudeia.feature.onboarding.data

import com.arrudeia.feature.onboarding.data.entity.OnboardingDataStoreUserRepositoryEntity

interface OnboardingDataStoreUserRepository {


    suspend fun saveUser(
        user:  OnboardingDataStoreUserRepositoryEntity
    ): Boolean

    suspend fun getUserData():  OnboardingDataStoreUserRepositoryEntity?

    suspend fun logoutUser(
    )
}