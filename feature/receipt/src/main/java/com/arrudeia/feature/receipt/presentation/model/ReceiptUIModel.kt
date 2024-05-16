package com.arrudeia.feature.receipt.presentation.model

import kotlinx.serialization.Serializable
import java.util.concurrent.TimeUnit

@Serializable
data class ReceiptUIModel(
    val uuid: Int? = 0,
    val name: String? = "",
    val time: Int? = 0,
    val quantity: String? = "",
    val img: String? = "",
    val level: String? = "",
) {
    fun getTimeFormatted(): String {
        time?.let {
            val hours = TimeUnit.SECONDS.toHours(it.toLong())
            val minutes = TimeUnit.SECONDS.toMinutes(it.toLong()) - TimeUnit.HOURS.toMinutes(hours)
            return String.format("%02d:%02d", hours, minutes)
        }
        return ""
    }
}

@Serializable
data class ReceiptDetailUiModel(
    val description: String?,
    val img: String?,
    val ingredients: List<IngredientReceiptDetailUiModel?>?,
    val level: String?,
    val name: String?,
    val quantity: String?,
    val time: Int?,
    val urlVideo: String?,

    ) {
    fun getTimeFormatted(): String {
        time?.let {
            val hours = TimeUnit.SECONDS.toHours(it.toLong())
            val minutes = TimeUnit.SECONDS.toMinutes(it.toLong()) - TimeUnit.HOURS.toMinutes(hours)
            return String.format("%02d:%02d", hours, minutes)
        }
        return ""
    }
}

@Serializable
data class IngredientReceiptDetailUiModel(
    val id: Int?,
    val recipeId: String?,
    val step: String?,
)