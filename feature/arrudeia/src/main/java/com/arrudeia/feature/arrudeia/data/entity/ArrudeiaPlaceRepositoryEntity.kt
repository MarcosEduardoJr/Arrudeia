package com.arrudeia.feature.arrudeia.data.entity

import com.arrudeia.core.data.GetArrudeiaPlacesQuery

class ArrudeiaPlaceRepositoryEntity(
    val available: List<AvailableRepositoryEntity>? = listOf(),
    val categoryName: String? = "",
    val description: String? = "",
    val image: String? = "",
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val name: String? = "",
    val phone: String? = "",
    val priceLevel: Int? = 0,
    val rating: Double? = 0.0,
    val socialNetwork: String? = "",
    val subCategoryName: String? = "",
    val uuid: String? = ""
)

public data class AvailableRepositoryEntity(
    public val id: String?,
    public val name: String?,
    public val placeId: String?,
)