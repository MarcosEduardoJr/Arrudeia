package com.arrudeia.feature.home.data

import com.arrudeia.feature.home.data.entity.ArrudeiaTvRepositoryEntity
import com.arrudeia.feature.home.data.entity.TravelRepositoryEntity


interface DefaultHomeTravelsRepository {
    suspend fun getAllTravels(): List<TravelRepositoryEntity>


    suspend fun getAllArrudeiaTv(): List<ArrudeiaTvRepositoryEntity>?


}
