package com.arrudeia.feature.services.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class ServiceExpertiseUiModel(
    val id: Int,
    val name: String,
)