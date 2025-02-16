package com.arrudeia.feature.home.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class PromotionUseCaseEntity(
    val name: String,
    val images: List<String>,
    val affiliate: String,
    val totalValue: String,
    val urlClick: String,
    val date: String
)
