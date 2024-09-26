package com.arrudeia.core.sign.domain

import com.arrudeia.core.data.repository.ProfileRepositoryImpl
import com.arrudeia.core.result.Result.Success
import com.arrudeia.core.sign.data.SignDataStoreUserRepository
import com.arrudeia.feature.sign.data.entity.SignDataStoreUserRepositoryEntity
import javax.inject.Inject

class CreateUserDataStoreUseCase @Inject constructor(
    private val repository: SignDataStoreUserRepository,
    private val repositoryProfile: ProfileRepositoryImpl,
) {
    suspend operator fun invoke(
        uid: String,
        name: String,
        email: String,
    ): Boolean {
        return when (val result = repositoryProfile.getUserPersonalInformationDetails(uid)) {
            is Success -> repository.saveUser(
                SignDataStoreUserRepositoryEntity(
                    uid,
                    name,
                    email,
                    isSavedDoc = result.data.idDocument?.isNotEmpty() == true,
                    image = result.data.profileImage.orEmpty()
                )
            )

            else -> false

        }
    }
}
