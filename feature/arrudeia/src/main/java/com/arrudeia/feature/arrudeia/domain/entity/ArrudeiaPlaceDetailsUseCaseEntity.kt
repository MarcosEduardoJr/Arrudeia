package com.arrudeia.feature.arrudeia.domain.entity

import com.google.android.gms.maps.model.LatLng

data class ArrudeiaPlaceDetailsUseCaseEntity(
    val uuid: String = "",
    val name: String = "",
    val categoryName: String = "",
    val subCategoryName: String = "",
    val phone: String = "",
    val socialNetwork: String = "",
    val description: String = "",
    val image: String = "",
    val location: LatLng? = LatLng(0.0, 0.0),
    val rating: Double? = 0.0,
    val priceLevel: Int?= 0,
    val comments: List<String>? = listOf(),
    val available: List<ArrudeiaAvailablePlaceUseCaseEntity>? = listOf(),
)
