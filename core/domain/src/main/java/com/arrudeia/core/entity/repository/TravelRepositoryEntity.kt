package com.arrudeia.core.entity.repository

import kotlinx.serialization.Serializable

@Serializable
data class TravelRepositoryEntity(
    val id: Long,
    val name: String = "",
    val city: String = "",
    val state: String = "",
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val price: Float = 0.0f,
    val discount: Int = 0,
    val cover_image_url: String = "",
    val whatsapp: Int = 0,


    )
