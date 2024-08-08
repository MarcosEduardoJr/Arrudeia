package com.arrudeia.core.places.data.entity

import com.arrudeia.core.graphql.GetArrudeiaPlacesQuery

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
    val rating: Int? = 0,
    val socialNetwork: String? = "",
    val subCategoryName: String? = "",
    val uuid: String? = "",
    val city: String? = "",
    val state: String? = "",
    val country: String? = ""
)

public data class AvailableRepositoryEntity(
    public val id: String?,
    public val name: String?,
    public val placeId: String?,
)