package com.arrudeia.feature.profile.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseUserRepositoryEntity(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
)
