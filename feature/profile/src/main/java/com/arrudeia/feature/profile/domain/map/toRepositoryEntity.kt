package com.arrudeia.feature.profile.domain.map

import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.domain.entity.UserAddressUseCaseEntity


fun UserAddressUseCaseEntity.toRepositoryEntity(): UserAddressRepositoryEntity {
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