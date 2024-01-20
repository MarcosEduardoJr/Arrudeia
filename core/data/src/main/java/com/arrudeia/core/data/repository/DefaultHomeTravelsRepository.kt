package com.arrudeia.core.data.repository
import com.arrudeia.core.data.interactions.Result
/**
 * Interface representing network calls to the Arrudeia backend
 */
interface DefaultHomeTravelsRepository {
    suspend fun getAllTravels(): Result<List<TravelRepositoryEntity>?>


    suspend fun getTravelById(id : Long): TravelRepositoryEntity?

    suspend fun getAllArrudeiaTv(): Result<List<ArrudeiaTvRepositoryEntity>?>
}
