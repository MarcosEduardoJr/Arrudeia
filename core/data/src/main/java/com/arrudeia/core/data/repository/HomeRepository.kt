package com.arrudeia.core.data.repository

import com.arrudeia.core.data.Syncable
import com.arrudeia.core.data.entity.TravelRepositoryEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository : Syncable {
    fun getAllTravel(): Flow<List<TravelRepositoryEntity>>
}
