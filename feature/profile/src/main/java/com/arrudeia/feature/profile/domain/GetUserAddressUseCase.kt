package com.arrudeia.feature.profile.domain

import com.arrudeia.feature.profile.data.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.domain.entity.UserAddressUseCaseEntity
import javax.inject.Inject

class GetUserAddressUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl,
    private val repositoryDataStore: ProfileDataStoreUserRepositoryImpl,
) {
    suspend operator fun invoke(): UserAddressUseCaseEntity? =
        repository.getUserAddress(repositoryDataStore.getUserData()?.uid.orEmpty())
            .toUseCaseEntity()

    private fun UserAddressRepositoryEntity?.toUseCaseEntity(): UserAddressUseCaseEntity? {
        var result: UserAddressUseCaseEntity? = null
        this?.let {
            result = UserAddressUseCaseEntity(
                uuid = this.uuid,
                zipCode = this.zipCode,
                street = this.street,
                number = this.number,
                district = this.district,
                city = this.city,
                state = this.state,
                country = this.country
            )
        }
        return result
    }

}
