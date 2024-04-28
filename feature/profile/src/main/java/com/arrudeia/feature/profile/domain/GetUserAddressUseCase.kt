package com.arrudeia.feature.profile.domain

import com.arrudeia.feature.profile.data.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.domain.entity.UserAddressUseCaseEntity
import javax.inject.Inject
import com.arrudeia.core.result.Result

class GetUserAddressUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl,
    private val repositoryDataStore: ProfileDataStoreUserRepositoryImpl,
) {
    suspend operator fun invoke(): Result<UserAddressUseCaseEntity?> =
        repository.getUserAddress(repositoryDataStore.getUserData()?.uid.orEmpty())
            .toUseCaseEntity()

    private fun Result<UserAddressRepositoryEntity?>.toUseCaseEntity() =
        when (this) {
            is Result.Success -> {
                Result.Success(
                    UserAddressUseCaseEntity(
                        uuid = data?.uuid,
                        zipCode = data?.zipCode,
                        street = data?.street,
                        number = data?.number,
                        district = data?.district,
                        city = data?.city,
                        state = data?.state,
                        country = data?.country
                    )
                )
            }

            is Result.Error -> {
                Result.Error(message)
            }

            else -> {
                Result.Error(null)
            }
        }
}
