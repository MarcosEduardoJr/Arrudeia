package com.arrudeia.feature.onboarding.domain

import com.arrudeia.feature.onboarding.data.OnboardingDataStoreUserRepository
import javax.inject.Inject

class SaveIsFirstTimeOpenUseCase @Inject constructor(
    private val repository: OnboardingDataStoreUserRepository,
) {
    suspend operator fun invoke(isFirstTimeOpen: Boolean) =
        repository.saveIsFirstTimeOpen(isFirstTimeOpen)
}
