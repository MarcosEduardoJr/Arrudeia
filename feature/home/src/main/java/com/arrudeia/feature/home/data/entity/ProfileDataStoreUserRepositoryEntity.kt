package com.arrudeia.feature.home.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDataStoreUserRepositoryEntity(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var image: String = "",
    var isSavedDoc: Boolean = false,
)
