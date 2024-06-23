package com.arrudeia.feature.services.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.entity.ServiceDetailRepositoryEntity


interface ServiceDetailRepository {
    suspend fun getService(id: Int): Result<ServiceDetailRepositoryEntity?>
}
