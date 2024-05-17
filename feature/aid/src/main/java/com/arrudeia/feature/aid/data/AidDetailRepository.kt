package com.arrudeia.feature.aid.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.aid.data.entity.AidDetailRepositoryEntity


interface AidDetailRepository {
    suspend fun getAid(id: String): Result<AidDetailRepositoryEntity?>
}
