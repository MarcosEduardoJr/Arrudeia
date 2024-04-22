package com.arrudeia.feature.onboarding.data

import com.arrudeia.feature.onboarding.data.entity.OnboardingDataStoreUserRepositoryEntity

interface OnboardingDataStoreUserRepository {
    suspend fun getUserData():  OnboardingDataStoreUserRepositoryEntity?

}