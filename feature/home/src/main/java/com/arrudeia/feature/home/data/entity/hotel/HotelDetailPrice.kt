package com.arrudeia.feature.home.data.entity.hotel

data class HotelDetailPrice(
    val link: String,
    val logo: String,
    val num_guests: Int,
    val official: Boolean,
    val rate_per_night: HotelDetailRatePerNight,
    val source: String,
    val total_rate: HotelDetailTotalRate
)