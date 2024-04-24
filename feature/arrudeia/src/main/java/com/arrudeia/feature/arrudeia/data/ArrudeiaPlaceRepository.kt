package com.arrudeia.feature.arrudeia.data

import android.net.Uri
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.arrudeia.core.graphql.GetArrudeiaPlacesQuery
import com.arrudeia.feature.arrudeia.data.entity.ArrudeiaPlaceRepositoryEntity
import com.arrudeia.feature.arrudeia.data.entity.toEntity
import javax.inject.Inject


interface ArrudeiaPlaceRepository {
    suspend fun getArrudeiaPlaces(): List<ArrudeiaPlaceRepositoryEntity>?
    suspend fun getArrudeiaPlace(uuid: String): ArrudeiaPlaceRepositoryEntity?
    suspend fun saveArrudeiaPlace(
        categoryName: String,
        description: String,
        image: String,
        latitude: Double,
        longitude: Double,
        name: String,
        phone: String,
        priceLevel: Int,
        rating: Double,
        socialNetwork: String,
        subCategoryName: String,
        uuid: String,
    ): String?

    suspend fun saveArrudeiaAvaliablePlace(
        name: String,
        placeId: String,
    ): String?
}


