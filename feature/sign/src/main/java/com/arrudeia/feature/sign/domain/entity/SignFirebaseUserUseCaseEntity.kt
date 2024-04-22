package com.arrudeia.feature.sign.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class SignFirebaseUserUseCaseEntity(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
)
