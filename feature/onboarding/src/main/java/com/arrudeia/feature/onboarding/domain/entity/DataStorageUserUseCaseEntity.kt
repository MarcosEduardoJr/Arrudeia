package com.arrudeia.feature.onboarding.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class DataStorageUserUseCaseEntity(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
)
