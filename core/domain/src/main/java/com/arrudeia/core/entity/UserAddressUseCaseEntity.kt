package com.arrudeia.core.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserAddressUseCaseEntity(
    val uuid: String?,
    val zipCode: String?,
    val street: String?,
    val number: Int?,
    val district: String?,
    val city: String?,
    val state: String?,
    val country: String?,
)
