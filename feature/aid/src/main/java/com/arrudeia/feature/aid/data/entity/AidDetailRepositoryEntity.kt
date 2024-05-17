package com.arrudeia.feature.aid.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class AidDetailRepositoryEntity(
    val id: Int?,
    val description: String?,
    val img: String?,
    val steps: List<AidStepRepositoryEntity?>?,
    val name: String?,
    val urlVideo: String?,
)

@Serializable
data class AidStepRepositoryEntity(
    val id: Int?,
    val aidId: String?,
    val step: String?,
)


