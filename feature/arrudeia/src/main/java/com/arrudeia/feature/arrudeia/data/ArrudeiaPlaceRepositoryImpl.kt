package com.arrudeia.feature.arrudeia.data

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.arrudeia.core.graphql.CreateArrudeiaAvailablePlaceMutation
import com.arrudeia.core.graphql.CreateArrudeiaPlaceMutation
import com.arrudeia.core.graphql.GetArrudeiaPlaceQuery
import com.arrudeia.core.graphql.GetArrudeiaPlacesQuery
import com.arrudeia.feature.arrudeia.data.entity.ArrudeiaPlaceRepositoryEntity
import com.arrudeia.feature.arrudeia.data.entity.toEntity
import javax.inject.Inject


class ArrudeiaPlaceRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ArrudeiaPlaceRepository {


    override suspend fun getArrudeiaPlaces(): List<ArrudeiaPlaceRepositoryEntity>? {
        val response = try {
            apolloClient.query(GetArrudeiaPlacesQuery()).execute()
        } catch (e: ApolloException) {
            return null
        }
        return response.data?.arrudeiaPlaces?.toEntity()
    }

    override suspend fun getArrudeiaPlace(uuid: String): ArrudeiaPlaceRepositoryEntity? {
        val response = try {
            apolloClient.query(GetArrudeiaPlaceQuery(uuid)).execute()
        } catch (e: ApolloException) {
            return null
        }
        return response.data?.arrudeiaPlace?.toEntity()
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
    ): String? {
        val response = try {
            apolloClient.mutation(
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
        } catch (e: ApolloException) {
            return null
        }
        return response.data?.createArrudeiaPlace
    }

    override suspend fun saveArrudeiaAvaliablePlace(
        name: String,
        placeId: String,
    ): String? {
        val response = try {
            apolloClient.mutation(
                CreateArrudeiaAvailablePlaceMutation(
                    name,
                    placeId,
                )
            ).execute()
        } catch (e: ApolloException) {
            return null
        }
        return response.data?.createArrudeiaAvailablePlace
    }
}


