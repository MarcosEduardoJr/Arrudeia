package com.arrudeia.feature.home.model

/**
 * External data layer representation of a Arrudeia Topic
 */
data class HomeTravelUiModel(
    val id: Long,
    val name: String = "",
    val city: String = "",
    val state: String = "",
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val price: Float = 0.0f,
    val discount: Int = 0,
    val cover_image_url: String = "",
    val whatsapp: Int = 0,

    )
