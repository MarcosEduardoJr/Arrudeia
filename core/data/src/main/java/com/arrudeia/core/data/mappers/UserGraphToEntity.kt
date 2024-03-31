package com.arrudeia.core.data.mappers

import com.arrudeia.core.data.GetUserAddressGraphQuery
import com.arrudeia.core.data.GetUserGraphQuery
import com.arrudeia.core.data.entity.UserAddressRepositoryEntity
import com.arrudeia.core.data.entity.UserPersonalInformationRepositoryEntity

fun GetUserGraphQuery.User.toEntity(): UserPersonalInformationRepositoryEntity {
    return UserPersonalInformationRepositoryEntity(
        uuid = uuid,
        name = name,
        email = email,
        idDocument = idDocument,
        birthDate = birthDate,
        profileImage = profileImage,
        phone = phone
    )
}

fun GetUserAddressGraphQuery.User.toAddressEntity(): UserAddressRepositoryEntity {
    return UserAddressRepositoryEntity(
        uuid = uuid,
        zipCode = this.zipCode,
        street = this.street,
        number = this.number,
        district = this.district,
        city = this.city,
        state = this.state,
        country = this.country
    )
}