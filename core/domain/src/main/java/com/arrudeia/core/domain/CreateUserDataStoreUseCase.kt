package com.arrudeia.core.domain

import com.arrudeia.core.data.repository.DataStoreUserRepository
import com.arrudeia.core.data.entity.DataStoreUserRepositoryEntity
import javax.inject.Inject

class CreateUserDataStoreUseCase @Inject constructor(
    private val repository: DataStoreUserRepository,
) {
    suspend operator fun invoke(uid: String, name: String, email: String) =
        repository.saveUser(DataStoreUserRepositoryEntity(uid, name, email))
}
