package com.arrudeia.feature.home.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ArrudeiaTvRepositoryEntity(
    val id:Int = 0,
    @Suppress("ConstructorParameterNaming")
    val image_url: String = "",
)
