package com.arrudeia.core.data.repository

import com.arrudeia.core.data.repository.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.core.graphql.GetUserAddressGraphQuery
import com.arrudeia.core.graphql.GetUserGraphQuery

fun GetUserGraphQuery.User.toEntity(): UserPersonalInformationRepositoryEntity {
    return  UserPersonalInformationRepositoryEntity(
        uuid = uuid,
        name = name,
        email = email,
        idDocument = idDocument,
        birthDate = birthDate,
        profileImage = profileImage,
        phone = phone
    )
}
