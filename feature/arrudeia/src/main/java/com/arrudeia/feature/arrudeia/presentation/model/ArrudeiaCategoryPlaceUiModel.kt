package com.arrudeia.feature.arrudeia.presentation.model

import com.arrudeia.feature.arrudeia.presentation.ui.CategoryOptions

data class ArrudeiaCategoryPlaceUiModel(
    var category: CategoryOptions,
    var icon: Int,
    var subcategories: List<ArrudeiaSubCategoryPlaceUiModel> = listOf(),
    var description: String = "",
    var rate: Float? = null,
    var price: Int? = null,
    var available: List<ArrudeiaAvailablePlaceUiModel> = listOf(),
    var uuidUSer: String = "",
)