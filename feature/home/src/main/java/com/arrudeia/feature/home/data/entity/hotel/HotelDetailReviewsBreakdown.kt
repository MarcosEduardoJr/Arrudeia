package com.arrudeia.feature.home.data.entity.hotel

data class HotelDetailReviewsBreakdown(
    val description: String,
    val name: String,
    val negative: Int,
    val neutral: Int,
    val positive: Int,
    val total_mentioned: Int
)