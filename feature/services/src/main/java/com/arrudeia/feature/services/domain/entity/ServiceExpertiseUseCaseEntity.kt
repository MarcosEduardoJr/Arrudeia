package com.arrudeia.feature.services.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ServiceExpertiseUseCaseEntity(
    val id: Int,
    val name: String,
)