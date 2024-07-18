package com.arrudeia.feature.arrudeia.presentation.model

import android.graphics.Bitmap
import com.arrudeia.feature.arrudeia.presentation.ui.CategoryOptions
import com.arrudeia.feature.arrudeia.presentation.ui.SubCategoryOptions
import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable

@Serializable
data class ArrudeiaPlaceDetailsUiModel(
    val uuid: String = "",
    val name: String = "",
    val categoryName: CategoryOptions = CategoryOptions.CATEGORY_FOOD,
    val subCategoryName: SubCategoryOptions = SubCategoryOptions.SUBCATEGORY_RESTAURANT,
    val phone: String = "",
    val socialNetwork: String = "",
    val description: String = "",
    val image: String = "",
    val location: LatLng? = LatLng(0.0, 0.0),
    val rating: Int? = 1,
    val priceLevel: Int? = 1,
    val comments: List<String>? = listOf(),
    val available: List<ArrudeiaAvailablePlaceUiModel>? = listOf(),
    var imageBitmap: Bitmap? = null,
    val city: String = "",
    val state: String = "",
    val country: String = "",
) {
    fun shortLocation() = city.plus(", ").plus(state)
}