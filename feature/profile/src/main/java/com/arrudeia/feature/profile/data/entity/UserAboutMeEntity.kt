package com.arrudeia.feature.profile.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserAboutMeEntity(
    val interests: String?,
    val biography: String?,
)

