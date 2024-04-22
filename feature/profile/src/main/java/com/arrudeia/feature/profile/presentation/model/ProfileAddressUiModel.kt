package com.arrudeia.feature.profile.presentation.model

class ProfileAddressUiModel(
    var uuid: String? = "",
    val zipCode: String?,
    val street: String?,
    val number: Int?,
    val district: String?,
    val city: String?,
    val state: String?,
    val country: String?,
)