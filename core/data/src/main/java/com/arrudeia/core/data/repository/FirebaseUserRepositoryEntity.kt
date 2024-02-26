package com.arrudeia.core.data.repository

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseUserRepositoryEntity(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
)
