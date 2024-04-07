package com.arrudeia.feature.arrudeia.model

data class ArrudeiaCategoryPlace(
    var name: String = "",
    var icon: Int,
    var subcategories: List<ArrudeiaSubCategoryPlace> = listOf(),
    var description: String = "",
    var rate: Float? = null,
    var price: Int? = null,
    var available: List<ArrudeiaAvailablePlace> = listOf(),
)