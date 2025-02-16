package com.arrudeia.feature.profile.data

import com.arrudeia.core.graphql.GetUserAboutMeQuery
import com.arrudeia.core.graphql.GetUserAddressGraphQuery
import com.arrudeia.core.graphql.GetUserGraphQuery
import com.arrudeia.feature.profile.data.entity.UserAboutMeEntity
import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.data.entity.UserPersonalInformationRepositoryEntity

fun GetUserGraphQuery.User.toEntity(): UserPersonalInformationRepositoryEntity {
    return UserPersonalInformationRepositoryEntity(
        uuid = uuid,
        name = name,
        email = email,
        idDocument = idDocument,
        birthDate = birthDate,
        profileImage = profileImage,
        phone = phone,
        gender = gender.orEmpty()
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

fun GetUserAboutMeQuery.User?.toAboutMeEntity(): UserAboutMeEntity {
    return UserAboutMeEntity(
        interests = this?.interests.orEmpty(),
        biography = this?.biography.orEmpty()
    )
}