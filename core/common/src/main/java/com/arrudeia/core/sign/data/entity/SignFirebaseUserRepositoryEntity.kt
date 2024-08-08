package com.arrudeia.feature.sign.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class SignFirebaseUserRepositoryEntity(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
)
