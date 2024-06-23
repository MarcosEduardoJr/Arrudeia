package com.arrudeia.feature.services.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ServiceUserRepositoryEntity(
    val uuidUserCreator: String?,
    val name: String?,
    val description: String?,
    val zipCode: String?,
    val street: String?,
    val number: Int?,
    val district: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val categoryId: Int?,
    val image: List<ServiceUserImageRepositoryEntity>
)

@Serializable
data class ServiceUserImageRepositoryEntity(
    val id: Int,
    val service_id: Int,
    val url: String,
)

