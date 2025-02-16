package com.arrudeia.feature.home.data.entity.hotel

data class HotelDetailSearchParameters(
    val adults: Int,
    val check_in_date: String,
    val check_out_date: String,
    val children: Int,
    val currency: String,
    val engine: String,
    val gl: String,
    val hl: String,
    val property_token: String,
    val q: String
)