package com.arrudeia.feature.services.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class CepAddressUseCaseEntity(
    val city: String = "",
    val country: String = "",
    val district: String = "",
    val state: String = "",
    val street: String = "",
    val zipCode: String = "",
)