package com.arrudeia.feature.home.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.home.data.HomeProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.home.data.HomeProfileRepositoryImpl
import com.arrudeia.feature.home.data.entity.ProfileDataStoreUserRepositoryEntity
import com.arrudeia.feature.home.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.feature.home.domain.entity.UserPersonalInformationUseCaseEntity
import javax.inject.Inject

class GetUserPersonalInformationUseCase @Inject constructor(
    private val repository: HomeProfileRepositoryImpl,
    private val repositoryDataStore: HomeProfileDataStoreUserRepositoryImpl,
) {
    suspend operator fun invoke(): Result<UserPersonalInformationUseCaseEntity?> =
        repository.getUserPersonalInformationDetails(
            repositoryDataStore.getUserData().getUid()
        ).toUseCaseEntity()


    private fun Result<ProfileDataStoreUserRepositoryEntity?>.getUid(): String {
        return when (this) {
            is Result.Success -> {
                this.data?.uid.orEmpty()
            }
            is Result.Error -> String()
            is Result.Loading -> String()
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
        }
    }
}
