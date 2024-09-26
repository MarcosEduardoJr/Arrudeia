package com.arrudeia.feature.profile.domain

import com.arrudeia.core.data.repository.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import javax.inject.Inject

class GetUserAboutMeUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl,
    private val repositoryDataStore: ProfileDataStoreUserRepositoryImpl,
) {
    suspend operator fun invoke() =
        repository.getUserAboutMe(repositoryDataStore.getUserData()?.uid.orEmpty())

}

