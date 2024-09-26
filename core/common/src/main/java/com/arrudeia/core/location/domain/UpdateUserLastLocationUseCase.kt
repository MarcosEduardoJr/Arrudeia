package com.arrudeia.core.location.domain

import com.arrudeia.core.data.repository.HomeProfileDataStoreUserRepositoryImpl
import com.arrudeia.core.location.data.repository.LastLocationRepositoryImpl
import javax.inject.Inject

class UpdateUserLastLocationUseCase @Inject constructor(
    private val useCase: LastLocationRepositoryImpl,
    private val repositoryDataStore: HomeProfileDataStoreUserRepositoryImpl
) {
    suspend operator fun invoke(
        lastCity: String,
        lastCountry: String
    ) {
        useCase.updateLastLocation(
            repositoryDataStore.getUuid().orEmpty(),
            lastCity,
            lastCountry
        )
    }
}
