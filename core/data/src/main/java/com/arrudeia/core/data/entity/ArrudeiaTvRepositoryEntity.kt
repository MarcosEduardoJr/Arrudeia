package com.arrudeia.core.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ArrudeiaTvRepositoryEntity(
    val id:Int = 0,
    val image_url: String = "",
)
