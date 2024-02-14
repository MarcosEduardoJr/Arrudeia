package com.arrudeia.feature.trip.model

import kotlinx.serialization.Serializable

@Serializable
data class TripUIModel(
    val id: Long,
    val name: String = "",
    val city: String = "",
    val state: String = "",
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val price: Int = 0,
    val discount: Int = 0,
    val cover_image_url: String = "",
    val whatsapp: String = "",
    val description: String = "",
    ){

    fun shortLocation() = city.plus(", ").plus(state)
    fun date() = day.toString().plus("/").plus(month.toString())
}
