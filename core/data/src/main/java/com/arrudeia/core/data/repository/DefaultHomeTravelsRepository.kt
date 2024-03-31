package com.arrudeia.core.data.repository
import com.arrudeia.core.data.entity.ArrudeiaTvRepositoryEntity
import com.arrudeia.core.data.entity.TravelRepositoryEntity

/**
 * Interface representing network calls to the Arrudeia backend
 */
interface DefaultHomeTravelsRepository {
    suspend fun getAllTravels(): List<TravelRepositoryEntity>


    suspend fun getTravelById(id : Long): TravelRepositoryEntity?

    suspend fun getAllArrudeiaTv(): List<ArrudeiaTvRepositoryEntity>?


}
