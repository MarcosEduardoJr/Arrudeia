package com.arrudeia.feature.aid.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class AidRepositoryEntity(
    val id: Int?,
    val name: String?,
    val description: String?,
    val img: String?,
    val urlVideo: String?,
)