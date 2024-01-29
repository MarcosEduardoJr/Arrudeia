package com.arrudeia.core.entity

import kotlinx.serialization.Serializable

@Serializable
data class ArrudeiaUseCaseEntity(
    val id: Int = 0,
    val imageUrl: String = "",
    )
