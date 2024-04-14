package com.arrudeia.feature.arrudeia.presentation.model

import com.arrudeia.feature.arrudeia.presentation.ui.CategoryOptions
import com.arrudeia.feature.arrudeia.presentation.ui.SubCategoryOptions
import com.google.android.gms.maps.model.LatLng

data class ArrudeiaPlaceDetailsUiModel(
    val uuid: String = "",
    val name: String = "",
    val categoryName: CategoryOptions,
    val subCategoryName: SubCategoryOptions,
    val phone: String = "",
    val socialNetwork: String = "",
    val description: String = "",
    val image: String = "",
    val location: LatLng? = LatLng(0.0, 0.0),
    val rating: Double? = 0.0,
    val priceLevel: Int?= 0,
    val comments: List<String>? = listOf(),
    val available: List<ArrudeiaAvailablePlaceUiModel>? = listOf(),
)