package com.arrudeia.feature.profile.domain

import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.domain.entity.UserAddressUseCaseEntity
import javax.inject.Inject

class UpdateUserAddressUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl
) {
    suspend operator fun invoke(user: UserAddressUseCaseEntity) =
        repository.updateUserAddress(user.toRepositoryEntity())


    private fun UserAddressUseCaseEntity.toRepositoryEntity(): UserAddressRepositoryEntity {
        return UserAddressRepositoryEntity(
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

}
