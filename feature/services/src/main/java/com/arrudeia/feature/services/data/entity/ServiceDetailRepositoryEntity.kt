package com.arrudeia.feature.services.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ServiceDetailRepositoryEntity(
    val categoryId: Int?,
    val city: String?,
    val country: String?,
    val description: String?,
    val district: String?,
    val id: Int?,
    val image: List<ServiceImageRepositoryEntity?>,
    val name: String?,
    val number: Int?,
    val state: String?,
    val street: String?,
    val uuidUserCreator: String?,
    val zipCode: String?,
)

@Serializable
data class ServiceImageRepositoryEntity(
    val id: Int?,
    val serviceId: Int?,
    val url: String?,
)


