package com.arrudeia.core.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class TravelRepositoryEntity(
    val id: Long = 0,
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
    )
