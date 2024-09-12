package com.arrudeia.feature.home.presentation.navigation.param

import kotlinx.serialization.Serializable

@Serializable
data class HotelDetailParam(
    var query: String = "",
    var checkInDate: String = "",
    var checkOutDate: String = "",
    var adults: Int = 0,
    var children: Int = 0,
    var childrenAges: String = "",
    var propertyToken: String = "",
    val amenities: List<String> = listOf<String>(),
)