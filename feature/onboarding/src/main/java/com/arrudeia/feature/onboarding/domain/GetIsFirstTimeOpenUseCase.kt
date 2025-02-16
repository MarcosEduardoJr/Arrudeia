package com.arrudeia.feature.onboarding.domain

import com.arrudeia.feature.onboarding.data.OnboardingDataStoreUserRepository
import javax.inject.Inject

class GetIsFirstTimeOpenUseCase @Inject constructor(
    private val repository: OnboardingDataStoreUserRepository,
) {
    suspend operator fun invoke() =
        repository.isFirstTimeOpen()
}
