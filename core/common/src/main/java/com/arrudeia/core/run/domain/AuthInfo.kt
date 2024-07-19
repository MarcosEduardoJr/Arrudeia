package com.arrudeia.core.run.domain

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
