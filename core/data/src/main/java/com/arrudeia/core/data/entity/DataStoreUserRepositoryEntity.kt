package com.arrudeia.core.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class DataStoreUserRepositoryEntity(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var image: String = "",
)
