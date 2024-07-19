package com.arrudeia.core.data.repository.entity

import kotlinx.serialization.Serializable

@Serializable
data class CepAddressRepositoryEntity(
    val city: String = "",
    val country: String = "",
    val district: String = "",
    val state: String = "",
    val street: String = "",
    val zipCode: String = "",
)