package com.arrudeia.feature.home.data.entity.hotel

data class HotelDetailNearbyPlace(
    val category: String,
    val description: String,
    val gps_coordinates: HotelDetailGpsCoordinates,
    val link: String,
    val name: String,
    val rating: Double,
    val reviews: Int,
    val thumbnail: String,
    val hotelDetailTransportations: List<HotelDetailTransportation>
)