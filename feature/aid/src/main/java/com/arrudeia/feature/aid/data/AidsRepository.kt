package com.arrudeia.feature.aid.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.aid.data.entity.AidRepositoryEntity


interface AidsRepository {
    suspend fun getAids(): Result<List<AidRepositoryEntity?>?>
}
