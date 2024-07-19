package com.arrudeia.feature.sign.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class SignDataStoreUserRepositoryEntity(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var image: String = "",
    var isSavedDoc: Boolean = false,
)
