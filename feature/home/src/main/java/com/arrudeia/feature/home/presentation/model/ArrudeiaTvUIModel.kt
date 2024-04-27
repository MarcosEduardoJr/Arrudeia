package com.arrudeia.feature.home.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class ArrudeiaTvUIModel(
    val id: Int = 0,
    val imageUrl: String = "",
)