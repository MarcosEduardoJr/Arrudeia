package com.arrudeia.feature.arrudeia.data

import android.net.Uri
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.arrudeia.core.graphql.GetArrudeiaPlacesQuery
import com.arrudeia.feature.arrudeia.data.entity.ArrudeiaPlaceRepositoryEntity
import com.arrudeia.feature.arrudeia.data.entity.toEntity
import javax.inject.Inject


import com.arrudeia.core.result.Result
interface ArrudeiaPlaceRepository {
    suspend fun getArrudeiaPlaces(): Result<List<ArrudeiaPlaceRepositoryEntity>?>
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
    ): Result<String?>

    suspend fun saveArrudeiaAvaliablePlace(
        name: String,
        placeId: String,
    ): Result<String?>
}


