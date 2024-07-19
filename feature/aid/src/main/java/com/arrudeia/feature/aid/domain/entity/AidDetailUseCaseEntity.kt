package com.arrudeia.feature.aid.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class AidDetailUseCaseEntity(
    val id: Int?,
    val description: String?,
    val img: String?,
    val steps: List<AidStepUseCaseEntity?>?,
    val name: String?,
    val urlVideo: String?,
)

@Serializable
data class AidStepUseCaseEntity(
    val id: Int?,
    val aidId: String?,
    val step: String?,
)