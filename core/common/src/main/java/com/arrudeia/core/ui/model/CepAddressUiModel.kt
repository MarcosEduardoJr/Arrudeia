package com.arrudeia.core.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class CepAddressUiModel(
    val city: String = "",
    val country: String = "",
    val district: String = "",
    val state: String = "",
    val street: String = "",
    val zipCode: String = "",
)