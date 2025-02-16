package com.arrudeia.feature.home.data.entity.hotel

data class Room(
    val images: List<String>,
    val link: String,
    val name: String,
    val num_guests: Int,
    val rate_per_night: HotelDetailRatePerNight,
    val total_rate: TotalRate
)