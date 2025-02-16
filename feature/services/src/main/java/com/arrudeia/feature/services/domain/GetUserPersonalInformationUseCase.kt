package com.arrudeia.feature.services.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.HomeProfileRepositoryImpl
import com.arrudeia.feature.services.data.entity.ServiceProfileDataStoreUserRepositoryEntity
import com.arrudeia.feature.services.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.feature.services.domain.entity.UserPersonalInformationUseCaseEntity
import javax.inject.Inject

class GetUserPersonalInformationUseCase @Inject constructor(
    private val repository: HomeProfileRepositoryImpl
) {
    suspend operator fun invoke(uuidUser : String): Result<UserPersonalInformationUseCaseEntity?> =
        repository.getUserPersonalInformationDetails(
             uuidUser
        ).toUseCaseEntity()


    private fun Result<ServiceProfileDataStoreUserRepositoryEntity?>.getUid(): String {
        return when (this) {
            is Result.Success -> {
                this.data?.uid.orEmpty()
            }
            is Result.Error -> String()
            is Result.Loading -> String()
            is Result.ErrorMessage -> TODO()
        }
    }

    private fun Result<UserPersonalInformationRepositoryEntity?>.toUseCaseEntity():
            Result<UserPersonalInformationUseCaseEntity?> {
        return when (this) {
            is Result.Success -> {
                val entity = this.data
                entity?.let {
                    Result.Success(
                        UserPersonalInformationUseCaseEntity(
                            uuid = it.uuid,
                            name = it.name,
                            email = it.email,
                            phone = it.phone,
                            idDocument = it.idDocument,
                            birthDate = it.birthDate,
                            profileImage = it.profileImage
                        )
                    )
                } ?: Result.Success(null)
            }

            is Result.Error -> Result.Error(this.message)
            is Result.Loading -> Result.Loading
            is Result.ErrorMessage -> TODO()
        }
    }
}
