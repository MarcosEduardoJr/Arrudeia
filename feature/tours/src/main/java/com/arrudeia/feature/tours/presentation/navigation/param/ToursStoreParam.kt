package com.arrudeia.feature.tours.presentation.navigation.param

import kotlinx.serialization.Serializable
@Serializable
data class ToursStoreParam(
    val storeId: String = "",
    val productId: String = ""
)