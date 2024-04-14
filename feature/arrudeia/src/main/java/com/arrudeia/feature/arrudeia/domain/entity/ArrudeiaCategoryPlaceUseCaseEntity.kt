package com.arrudeia.feature.arrudeia.domain.entity

data class ArrudeiaCategoryPlaceUseCaseEntity(
    var name: String = "",
    var icon: Int,
    var subcategories: List<ArrudeiaSubCategoryPlaceUseCaseEntity> = listOf(),
    var description: String = "",
    var rate: Float? = null,
    var price: Int? = null,
    var available: List<ArrudeiaAvailablePlaceUseCaseEntity> = listOf(),
    var uuidUSer: String = "",
)