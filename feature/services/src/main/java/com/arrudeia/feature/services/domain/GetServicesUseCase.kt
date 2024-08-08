package com.arrudeia.feature.services.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.ServiceRepository
import com.arrudeia.feature.services.data.ServiceRepositoryImpl
import com.arrudeia.feature.services.domain.entity.ServiceCaseEntity
import javax.inject.Inject

class GetServicesUseCase @Inject constructor(
    private val repository: ServiceRepositoryImpl,
) {
    suspend operator fun invoke():
            Result<List<ServiceCaseEntity>> =
        repository.getServices().toServicesUseCaseEntity()
}
