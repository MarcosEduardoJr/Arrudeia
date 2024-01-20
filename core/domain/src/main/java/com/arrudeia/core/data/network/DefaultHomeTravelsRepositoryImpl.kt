package com.arrudeia.core.data.network

import com.arrudeia.core.data.repository.DefaultHomeTravelsRepository
import com.arrudeia.core.data.repository.TravelRepositoryEntity
import com.arrudeia.core.network.BuildConfig
import com.arrudeia.core.network.model.ArrudeiaNetworkError
import com.arrudeia.core.network.model.NetworkTravel
import com.arrudeia.core.network.retrofit.service.RetrofitArrudeiaNetworkService
import com.google.gson.Gson
import javax.inject.Inject
import com.arrudeia.core.data.interactions.Result
import com.arrudeia.core.data.interactions.Result.Success
import com.arrudeia.core.data.interactions.Result.Failure
import com.arrudeia.core.data.repository.ArrudeiaTvRepositoryEntity
import com.arrudeia.core.network.model.NetworkArrudeiaTv

private const val ALL_TRAVEL = BuildConfig.ALL_TRAVEL
private const val TYPE_DRIVE_EXPORT = BuildConfig.TYPE_DRIVE_EXPORT
private const val ARRUDEIA_TV = BuildConfig.ARRUDEIA_TV

class DefaultHomeTravelsRepositoryImpl @Inject constructor(
    private val api: RetrofitArrudeiaNetworkService,
) : DefaultHomeTravelsRepository {

    override suspend fun getAllTravels(): Result<List<TravelRepositoryEntity>?> {
        val response = api.getAllTravels(ALL_TRAVEL, TYPE_DRIVE_EXPORT)
        return when (response.code()) {
            200, 201, 204 -> Success(response.body()?.mapTravelToRepositoryEntity())
            else -> {
                try {
                    val error =
                        Gson().fromJson(
                            response.errorBody()?.charStream(),
                            ArrudeiaNetworkError::class.java
                        )
                    Failure(Exception(error.httpMessage))
                } catch (ex: Exception) {
                    Failure(Exception())
                }
            }
        }
    }

    override suspend fun getTravelById(id: Long): TravelRepositoryEntity? {
        var result: TravelRepositoryEntity? = null
        // getAllTravels()?..forEach { if (it.id == id) result = it }
        return result
    }

    override suspend fun getAllArrudeiaTv(): Result<List<ArrudeiaTvRepositoryEntity>?> {
        val response = api.getAllArrudeiaTv(ARRUDEIA_TV, TYPE_DRIVE_EXPORT)
        return when (response.code()) {
            200, 201, 204 -> Success(response.body()?.mapArrudeiaTvToRepositoryEntity())
            else -> {
                try {
                    val error =
                        Gson().fromJson(
                            response.errorBody()?.charStream(),
                            ArrudeiaNetworkError::class.java
                        )
                    Failure(Exception(error.httpMessage))
                } catch (ex: Exception) {
                    Failure(Exception())
                }
            }
        }
    }

    private fun List<NetworkTravel>.mapTravelToRepositoryEntity(): List<TravelRepositoryEntity> {
        var resultList = mutableListOf<TravelRepositoryEntity>()
        forEach {
            val item = TravelRepositoryEntity(
                id = it.id,
                name = it.name,
                city = it.city,
                state = it.state,
                day = it.day,
                month = it.month,
                year = it.year,
                price = it.price,
                discount = it.discount,
                cover_image_url = it.cover_image_url,
                whatsapp = it.whatsapp,
            )
            resultList.add(item)
        }
        return (resultList)
    }

    private fun List<NetworkArrudeiaTv>.mapArrudeiaTvToRepositoryEntity(): List<ArrudeiaTvRepositoryEntity> {
        var resultList = mutableListOf<ArrudeiaTvRepositoryEntity>()
        forEach {
            val item = ArrudeiaTvRepositoryEntity(
                image_url = it.imageUrl
            )
            resultList.add(item)
        }
        return (resultList)
    }
}


