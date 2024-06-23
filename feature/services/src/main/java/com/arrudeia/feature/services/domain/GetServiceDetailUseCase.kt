package com.arrudeia.feature.services.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.ServiceDetailRepositoryImpl
import com.arrudeia.feature.services.domain.entity.ServiceDetailUseCaseEntity
import javax.inject.Inject

class GetServiceDetailUseCase @Inject constructor(
    private val repository: ServiceDetailRepositoryImpl,
) {
    suspend operator fun invoke(id: Int):
            Result<ServiceDetailUseCaseEntity?> =
        repository.getService(id).toServiceDetailUseCaseEntity()
}
