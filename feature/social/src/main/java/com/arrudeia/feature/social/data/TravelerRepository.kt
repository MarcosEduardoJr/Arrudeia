package com.arrudeia.feature.social.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.social.data.entity.TravelersEntity

interface TravelerRepository {

    suspend fun getTravelers(uuid: String) : Result<List<TravelersEntity>?>
}