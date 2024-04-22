package com.arrudeia.feature.onboarding.domain

import com.arrudeia.feature.onboarding.data.OnboardingDataStoreUserRepository
import com.arrudeia.feature.onboarding.data.entity.OnboardingDataStoreUserRepositoryEntity
import com.arrudeia.feature.onboarding.domain.entity.DataStorageUserUseCaseEntity
import javax.inject.Inject

class GetCurrentUserDataStoreUseCase @Inject constructor(
    private val repository: OnboardingDataStoreUserRepository,
) {
    suspend operator fun invoke() =
        repository.getUserData().matToUseCase()


    private fun OnboardingDataStoreUserRepositoryEntity?.matToUseCase(): DataStorageUserUseCaseEntity? {
        return if (this == null)
            null
        else
            DataStorageUserUseCaseEntity(
                this.uid,
                this.name,
                this.email,
            )
    }
}
