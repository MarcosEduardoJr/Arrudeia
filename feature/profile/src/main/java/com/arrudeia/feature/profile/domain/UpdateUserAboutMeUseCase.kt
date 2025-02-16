package com.arrudeia.feature.profile.domain

import com.arrudeia.core.result.Result
import com.arrudeia.core.data.repository.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import javax.inject.Inject

class UpdateUserAboutMeUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl,
    private val repositoryDataStore: ProfileDataStoreUserRepositoryImpl,
) {
    suspend operator fun invoke(interests: String, description: String): Result<String?> {
        return repository.updateUserAboutMe(
            repositoryDataStore.getUserData()?.uid.orEmpty(),
            interests,
            description
        )
    }
}
