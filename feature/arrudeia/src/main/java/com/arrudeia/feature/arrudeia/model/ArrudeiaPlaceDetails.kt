package com.arrudeia.feature.arrudeia.model

import com.google.android.gms.maps.model.LatLng

data class ArrudeiaPlaceDetails(
    val name: String = "",
    val type: String = "",
    val location: LatLng = LatLng(0.0, 0.0),
    val rating: Float = 0.0f,
    val priceLevel: Int = 0,
    val comments: List<String>? = listOf()
)