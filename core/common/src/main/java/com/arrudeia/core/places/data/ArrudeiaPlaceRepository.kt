package com.arrudeia.core.places.data

import android.net.Uri
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.arrudeia.core.graphql.GetArrudeiaPlacesQuery
import com.arrudeia.core.places.data.entity.ArrudeiaPlaceRepositoryEntity
import com.arrudeia.core.places.data.entity.toEntity
import javax.inject.Inject


import com.arrudeia.core.result.Result
interface ArrudeiaPlaceRepository {
    suspend fun getArrudeiaPlaces(state : String): Result<List<ArrudeiaPlaceRepositoryEntity>?>
    suspend fun saveArrudeiaPlace(
        categoryName: String,
        description: String,
        image: String,
        latitude: Double,
        longitude: Double,
        name: String,
        phone: String,
        priceLevel: Int,
        rating: Int,
        socialNetwork: String,
        subCategoryName: String,
        uuid: String,
        city: String,
        state: String,
        country: String
    ): Result<String?>

    suspend fun saveArrudeiaAvaliablePlace(
        name: String,
        placeId: String,
    ): Result<String?>
}


