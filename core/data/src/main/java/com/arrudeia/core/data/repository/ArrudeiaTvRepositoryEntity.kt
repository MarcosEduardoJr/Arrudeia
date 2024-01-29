package com.arrudeia.core.data.repository

import kotlinx.serialization.Serializable

@Serializable
data class ArrudeiaTvRepositoryEntity(
    val id:Int = 0,
    val image_url: String = "",
)
