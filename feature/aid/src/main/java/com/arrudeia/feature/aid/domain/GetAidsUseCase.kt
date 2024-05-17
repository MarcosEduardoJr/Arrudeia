package com.arrudeia.feature.aid.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.aid.data.AidsRepositoryImpl
import com.arrudeia.feature.aid.domain.entity.AidUseCaseEntity
import javax.inject.Inject

class GetAidsUseCase @Inject constructor(
    private val repository: AidsRepositoryImpl,
) {
    suspend operator fun invoke():
            Result<List<AidUseCaseEntity?>?> =
        repository.getAids().mapToUseCaseEntity()
}
