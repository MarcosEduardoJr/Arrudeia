package com.arrudeia.feature.services.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ServiceRepositoryEntity(
    val id: Int?,
    val categoryId: Int?,
    val name: String?,
    val description: String?,
    val city: String?,
    val country: String?,
    val imageUrl: String?,
)