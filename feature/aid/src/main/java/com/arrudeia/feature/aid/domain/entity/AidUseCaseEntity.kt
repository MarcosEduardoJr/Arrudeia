package com.arrudeia.feature.aid.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class AidUseCaseEntity(
    val id: Int?,
    val name: String?,
    val description: String?,
    val img: String?,
    val urlVideo: String?,
)
