package com.arrudeia.core.data.repository

import kotlinx.serialization.Serializable

@Serializable
data class DataStoreUserRepositoryEntity(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
)
