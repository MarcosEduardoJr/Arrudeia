package com.arrudeia.core.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseUserRepositoryEntity(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
)
