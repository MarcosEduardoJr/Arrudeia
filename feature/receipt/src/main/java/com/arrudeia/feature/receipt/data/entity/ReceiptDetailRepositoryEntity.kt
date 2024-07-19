package com.arrudeia.feature.receipt.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReceiptDetailRepositoryEntity(
    val description: String?,
    val img: String?,
    val ingredients: List<IngredientReceiptDetailRepositoryEntity?>?,
    val level: String?,
    val name: String?,
    val quantity: String?,
    val time: Int?,
    val urlVideo: String?,
)

@Serializable
data class IngredientReceiptDetailRepositoryEntity(
    val id: Int?,
    val recipeId: String?,
    val step: String?,
)
