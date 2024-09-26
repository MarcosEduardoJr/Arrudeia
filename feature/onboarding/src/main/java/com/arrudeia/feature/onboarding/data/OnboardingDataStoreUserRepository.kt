package com.arrudeia.feature.onboarding.data

import com.arrudeia.core.result.Result

interface OnboardingDataStoreUserRepository {
    suspend fun isUserSaved():  Result<Boolean?>

    suspend fun isFirstTimeOpen(): Boolean

    suspend fun saveIsFirstTimeOpen(isFirstTimeOpen: Boolean)

}