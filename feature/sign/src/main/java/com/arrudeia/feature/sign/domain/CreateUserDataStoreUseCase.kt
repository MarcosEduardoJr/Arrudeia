package com.arrudeia.feature.sign.domain

import com.arrudeia.feature.sign.data.SignDataStoreUserRepository
import com.arrudeia.feature.sign.data.entity.SignDataStoreUserRepositoryEntity
import javax.inject.Inject

class CreateUserDataStoreUseCase @Inject constructor(
    private val repository: SignDataStoreUserRepository,
) {
    suspend operator fun invoke(uid: String, name: String, email: String) =
        repository.saveUser(
            SignDataStoreUserRepositoryEntity(
                uid,
                name,
                email
            )
        )
}
