package com.arrudeia.feature.home.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ArrudeiaUseCaseEntity(
    val id: Int = 0,
    val imageUrl: String = "",
    )
