package com.arrudeia.feature.trip.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class TravelUseCaseEntity(
    val id: Long = 0,
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
    val description: String = "",
    val include: List<String> = listOf(),
    val optional: List<String> = listOf(),
    )
