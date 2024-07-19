package com.arrudeia.feature.services.data

import com.arrudeia.core.graphql.GetServiceQuery
import com.arrudeia.core.graphql.GetServicesExpertiseQuery
import com.arrudeia.core.graphql.GetServicesQuery
import com.arrudeia.core.graphql.GetUserGraphQuery
import com.arrudeia.core.graphql.type.ImageInput
import com.arrudeia.core.graphql.type.Service
import com.arrudeia.feature.services.data.entity.ServiceDetailRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceExpertiseRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceImageRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceUserImageRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceUserRepositoryEntity
import com.arrudeia.feature.services.data.entity.UserPersonalInformationRepositoryEntity

fun List<GetServicesQuery.Service?>?.toServicesRepositoryEntity(): List<ServiceRepositoryEntity?>? {
    return if (this == null) null
    else {
        val list = mutableListOf<ServiceRepositoryEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        ServiceRepositoryEntity(
                            id = item.id,
                            name = item.name,
                            description = item.description,
                            categoryId = item.categoryId,
                            city = item.city,
                            country = item.country,
                            imageUrl = item.imageUrl
                        )
                    )
                }
            }
        }
        list.toList()
    }
}


fun GetServiceQuery.Service?.toServiceDetailRepositoryEntity(): ServiceDetailRepositoryEntity? {
    return if (this == null) null
    else {
        ServiceDetailRepositoryEntity(
            categoryId = this.categoryId,
            city = this.city,
            country = this.country,
            description = this.description,
            district = this.district,
            id = this.id,
            image = this.image.toServiceImageRepositoryEntity(),
            name = this.name,
            number = this.number,
            state = this.state,
            street = this.street,
            uuidUserCreator = this.uuidUserCreator,
            zipCode = this.zipCode,
        )

    }
}

fun List<GetServiceQuery.Image?>?.toServiceImageRepositoryEntity(): List<ServiceImageRepositoryEntity?> {
    return if (this == null) listOf()
    else {
        val list = mutableListOf<ServiceImageRepositoryEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        ServiceImageRepositoryEntity(
                            id = item.id,
                            serviceId = item.serviceId,
                            url = item.url,
                        )
                    )
                }
            }
        }
        list.toList()
    }
}


fun ServiceUserRepositoryEntity.toServiceUserRepositoryEntity(): Service {
    return Service(
        uuidUserCreator = this.uuidUserCreator.orEmpty(),
        name = this.name.orEmpty(),
        description = this.description.orEmpty(),
        zipCode = this.zipCode.orEmpty(),
        street = this.street.orEmpty(),
        number = this.number ?: 0,
        district = this.district.orEmpty(),
        city = this.city.orEmpty(),
        state = this.state.orEmpty(),
        country = this.country.orEmpty(),
        categoryId = this.categoryId ?: 0,
        image = this.image.toServiceUserImageRepositoryEntity()
    )
}


fun List<ServiceUserImageRepositoryEntity?>?.toServiceUserImageRepositoryEntity(): List<ImageInput?> {
    return if (this == null) listOf()
    else {
        val list = mutableListOf<ImageInput>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        ImageInput(
                            id = item.id,
                            serviceId = item.service_id,
                            url = item.url,
                        )
                    )
                }
            }
        }
        list.toList()
    }
}


fun List<GetServicesExpertiseQuery.ServicesExpertise?>.toServicesExpertiseRepositoryEntity(): List<ServiceExpertiseRepositoryEntity> {
    return if (this == null) listOf()
    else {
        val list = mutableListOf<ServiceExpertiseRepositoryEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        ServiceExpertiseRepositoryEntity(
                            id = item.id,
                            name = item.name
                        )
                    )
                }
            }
        }
        list.toList()
    }
}

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