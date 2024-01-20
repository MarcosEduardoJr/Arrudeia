package com.arrudeia.core.entity

import kotlinx.serialization.Serializable

@Serializable
data class TravelUseCaseEntity(
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


    )
