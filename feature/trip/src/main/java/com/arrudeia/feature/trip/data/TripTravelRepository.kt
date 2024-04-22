package com.arrudeia.feature.trip.data

import com.arrudeia.feature.trip.data.entity.TravelRepositoryEntity


interface TripTravelRepository {
    suspend fun getAllTravels(): List<TravelRepositoryEntity>


    suspend fun getTravelById(id : Long): TravelRepositoryEntity?



}
