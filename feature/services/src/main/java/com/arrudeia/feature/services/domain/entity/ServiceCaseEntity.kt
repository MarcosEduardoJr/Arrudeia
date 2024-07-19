package com.arrudeia.feature.services.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class     ServiceCaseEntity(
    val id: Int?,
    val categoryId: Int?,
    val name: String?,
    val description: String?,
    val city: String?,
    val country: String?,
    val imageUrl: String?,
)
