package com.arrudeia.feature.services.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.entity.ServiceExpertiseRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceUserRepositoryEntity


interface ServiceRepository {
    suspend fun getServices(): Result<List<ServiceRepositoryEntity?>?>

    suspend fun createService(service: ServiceUserRepositoryEntity): Result<Int?>

    suspend fun getServicesCategoriesExpertise(): Result<List<ServiceExpertiseRepositoryEntity?>?>
}
