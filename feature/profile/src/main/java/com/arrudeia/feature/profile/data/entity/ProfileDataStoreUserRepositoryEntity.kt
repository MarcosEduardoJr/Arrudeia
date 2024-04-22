package com.arrudeia.feature.profile.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDataStoreUserRepositoryEntity(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var image: String = "",
)
