package com.arrudeia.feature.services.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ServiceProfileDataStoreUserRepositoryEntity(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var image: String = "",
)
