package com.arrudeia.core.sign.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class SignFirebaseUserUseCaseEntity(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val img: String = "",
)
