package com.arrudeia.feature.home.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDataStoreUseCaseEntity(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var image: String = "",
)
