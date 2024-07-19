package com.arrudeia.feature.services.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ServiceExpertiseRepositoryEntity(
    val id: Int,
    val name: String,
)