package com.arrudeia.feature.aid.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.aid.data.AidDetailRepositoryImpl
import com.arrudeia.feature.aid.domain.entity.AidDetailUseCaseEntity
import javax.inject.Inject

class GetAidDetailUseCase @Inject constructor(
    private val repository: AidDetailRepositoryImpl,
) {
    suspend operator fun invoke(id: String):
            Result<AidDetailUseCaseEntity?> =
        repository.getAid(id).toEntity()
}
