package com.arrudeia.feature.arrudeia.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.CreateArrudeiaAvailablePlaceMutation
import com.arrudeia.core.graphql.CreateArrudeiaPlaceMutation
import com.arrudeia.core.graphql.GetArrudeiaPlacesQuery
import com.arrudeia.core.result.Result
import com.arrudeia.feature.arrudeia.data.entity.ArrudeiaPlaceRepositoryEntity
import com.arrudeia.feature.arrudeia.data.entity.toEntity
import javax.inject.Inject


class ArrudeiaPlaceRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ArrudeiaPlaceRepository {


    override suspend fun getArrudeiaPlaces(): Result<List<ArrudeiaPlaceRepositoryEntity>?> {
        val response = apolloClient.query(GetArrudeiaPlacesQuery()).execute()
        if (response.hasErrors() || response.data?.arrudeiaPlaces.toEntity() == null)
            return Result.Error(null)
        return Result.Success(response.data!!.arrudeiaPlaces!!.toEntity())
    }

    override suspend fun saveArrudeiaPlace(
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
    ): Result<String?> {
        val response = apolloClient.mutation(
            CreateArrudeiaPlaceMutation(
                categoryName,
                description,
                image,
                latitude,
                longitude,
                name,
                phone,
                priceLevel,
                rating,
                socialNetwork,
                subCategoryName,
                uuid
            )
        ).execute()
        if (response.hasErrors() || response.data?.createArrudeiaPlace == null)
            return Result.Error(null)
        return Result.Success(response.data?.createArrudeiaPlace.orEmpty())
    }

    override suspend fun saveArrudeiaAvaliablePlace(
        name: String,
        placeId: String,
    ): Result<String?> {
        val response = apolloClient.mutation(
            CreateArrudeiaAvailablePlaceMutation(
                name,
                placeId,
            )
        ).execute()
        if (response.hasErrors() || response.data?.createArrudeiaAvailablePlace == null)
            return Result.Error(null)
        return Result.Success(response.data?.createArrudeiaAvailablePlace)
    }
}


