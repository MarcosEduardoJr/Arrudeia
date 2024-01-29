package com.arrudeia.feature.home.model

import kotlinx.serialization.Serializable

@Serializable
data class ArrudeiaTvUIModel(
    val id: Int = 0,
    val imageUrl: String = "",
)