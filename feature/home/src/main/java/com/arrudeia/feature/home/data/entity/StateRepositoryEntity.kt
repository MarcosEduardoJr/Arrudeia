package com.arrudeia.feature.home.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class StateRepositoryEntity(
    @Suppress("ConstructorParameterNaming")
    val name: String = "",
)
