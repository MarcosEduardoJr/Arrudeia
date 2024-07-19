package com.arrudeia.feature.receipt.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReceiptDetailUseCaseEntity(
    val description: String?,
    val img: String?,
    val ingredients: List<IngredientReceiptDetailUseCaseEntity?>?,
    val level: String?,
    val name: String?,
    val quantity: String?,
    val time: Int?,
    val urlVideo: String?,
)

@Serializable
data class IngredientReceiptDetailUseCaseEntity(
    val id: Int?,
    val recipeId: String?,
    val step: String?,
)