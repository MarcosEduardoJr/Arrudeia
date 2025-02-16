package com.arrudeia.feature.home.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class HotelUIModel(
    val id: Long,
    val name: String = "",
    val overallRating: Double,
    val reviews: Int,
    val listAmenities: List<String> = emptyList(),
    val price: String = "",
    val image: String = "",
    )
