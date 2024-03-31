package com.arrudeia.core.domain

import com.arrudeia.core.data.repository.DataStoreUserRepository
import com.arrudeia.core.data.entity.DataStoreUserRepositoryEntity
import com.arrudeia.core.entity.DataStorageUserUseCaseEntity
import javax.inject.Inject

class GetCurrentUserDataStoreUseCase @Inject constructor(
    private val repository: DataStoreUserRepository,
) {
    suspend operator fun invoke() =
        repository.getUserData().matToUseCase()


    private fun DataStoreUserRepositoryEntity?.matToUseCase(): DataStorageUserUseCaseEntity? {
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
