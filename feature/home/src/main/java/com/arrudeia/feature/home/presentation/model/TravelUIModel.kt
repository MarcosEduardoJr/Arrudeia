package com.arrudeia.feature.home.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class TravelUIModel(
    val id: Long,
    val name: String = "",
    val city: String = "",
    val state: String = "",
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val price: Int = 0,
    val discount: Int = 0,
    val coverImageUrl: String = "",
    val whatsapp: String = "",
    ){

    fun shortLocation() = city.plus(", ").plus(state)
    fun date() = day.toString().plus("/").plus(month.toString())
}
