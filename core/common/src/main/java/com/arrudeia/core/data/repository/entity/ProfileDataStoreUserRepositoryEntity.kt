package com.arrudeia.core.data.repository.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDataStoreUserRepositoryEntity(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var image: String = "",
    var isSavedDoc: Boolean = false,
)
