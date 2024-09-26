package com.arrudeia.feature.profile.domain

import com.arrudeia.core.result.Result
import com.arrudeia.core.data.repository.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import com.arrudeia.feature.profile.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.feature.profile.domain.entity.UserPersonalInformationUseCaseEntity
import javax.inject.Inject

class GetUserPersonalInformationUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl,
    private val repositoryDataStore: ProfileDataStoreUserRepositoryImpl,
) {
    suspend operator fun invoke(): Result<UserPersonalInformationUseCaseEntity> =
        repository.getUserPersonalInformationDetails(repositoryDataStore.getUserData()?.uid.orEmpty())
            .toUseCaseEntity()

    private fun Result<UserPersonalInformationRepositoryEntity>.toUseCaseEntity():
            Result<UserPersonalInformationUseCaseEntity> {
        val result: Result<UserPersonalInformationUseCaseEntity>
        when (this) {
            is Result.Success -> {
                result = Result.Success(
                    UserPersonalInformationUseCaseEntity(
                        uuid = data?.uuid,
                        name = data?.name,
                        email = data?.email,
                        phone = data?.phone,
                        idDocument = data?.idDocument,
                        birthDate = data?.birthDate,
                        profileImage = data?.profileImage,
                        gender = data?.gender.orEmpty()
                    )
                )
            }

            is Result.Error -> {
                result = Result.Error(message)
            }

            else -> {
                result = Result.Error(null)
            }
        }
        return result
    }
}
