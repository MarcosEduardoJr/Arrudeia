package com.arrudeia.core.data.repository

import com.arrudeia.core.data.Syncable
import kotlinx.coroutines.flow.Flow

interface HomeRepository : Syncable {
    fun getAllTravel(): Flow<List<TravelRepositoryEntity>>
}
