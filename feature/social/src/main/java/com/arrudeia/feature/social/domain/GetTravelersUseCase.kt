package com.arrudeia.feature.social.domain


import com.arrudeia.core.data.repository.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.social.data.TravelerRepositoryImpl
import javax.inject.Inject

class GetTravelersUseCase @Inject constructor(
    private val repository: TravelerRepositoryImpl,
    private val repositoryDataStore: ProfileDataStoreUserRepositoryImpl,
) {
    suspend operator fun invoke(page: Int) =     repository.getTravelers(repositoryDataStore.getUserData()?.uid.orEmpty(),page)

}