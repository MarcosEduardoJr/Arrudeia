package com.arrudeia.feature.home.data.entity.hotel

data class FeaturedPrice(
    val link: String?,
    val logo: String?,
    val num_guests: Int?,
    val official: Boolean,
    val rate_per_night: HotelDetailRatePerNight,
    val remarks: List<String>?,
    val rooms: List<Room>?,
    val source: String?,
    val total_rate: HotelDetailTotalRate?
)