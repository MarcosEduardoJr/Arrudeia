package com.arrudeia.feature.services.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.ServiceRepository
import com.arrudeia.feature.services.data.ServiceRepositoryImpl
import com.arrudeia.feature.services.domain.entity.ServiceCaseEntity
import com.arrudeia.feature.services.domain.entity.ServiceExpertiseUseCaseEntity
import javax.inject.Inject

class GetServicesExpertiseUseCase @Inject constructor(
    private val repository: ServiceRepositoryImpl,
) {
    suspend operator fun invoke():
            Result<List<ServiceExpertiseUseCaseEntity>> =
        repository.getServicesCategoriesExpertise().toEntity()
}
