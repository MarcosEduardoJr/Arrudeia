package com.arrudeia.feature.services.domain.entity

import com.arrudeia.feature.services.data.entity.ServiceImageRepositoryEntity
import kotlinx.serialization.Serializable

@Serializable
data class ServiceDetailUseCaseEntity(
    val categoryId: Int?,
    val city: String?,
    val country: String?,
    val description: String?,
    val district: String?,
    val id: Int?,
    val image: List<ServiceImageUseCaseEntity?>,
    val name: String?,
    val number: Int?,
    val state: String?,
    val street: String?,
    val uuidUserCreator: String?,
    val zipCode: String?,
)

@Serializable
data class ServiceImageUseCaseEntity(
    val id: Int?,
    val serviceId: Int?,
    val url: String?,
)