package com.arrudeia.feature.trip.data

import com.arrudeia.feature.trip.data.entity.TravelRepositoryEntity

import com.arrudeia.core.result.Result

interface TripTravelRepository {
    suspend fun getAllTravels(): Result<List<TravelRepositoryEntity>>


    suspend fun getTravelById(id : Long): Result<TravelRepositoryEntity?>



}
