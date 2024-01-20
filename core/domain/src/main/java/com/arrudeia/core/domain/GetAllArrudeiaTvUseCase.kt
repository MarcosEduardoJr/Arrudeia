package com.arrudeia.core.domain

import com.arrudeia.core.data.interactions.Result
import com.arrudeia.core.data.network.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.core.data.repository.ArrudeiaTvRepositoryEntity
import com.arrudeia.core.data.repository.TravelRepositoryEntity
import com.arrudeia.core.entity.ArrudeiaUseCaseEntity
import com.arrudeia.core.entity.TravelUseCaseEntity
import javax.inject.Inject

class GetAllArrudeiaTvUseCase @Inject constructor(
    private val repository: DefaultHomeTravelsRepositoryImpl,
) {
    suspend operator fun invoke(): Result<List<ArrudeiaUseCaseEntity>?> =
        repository.getAllArrudeiaTv().mapTolUseCaseEntity()

    private fun Result<List<ArrudeiaTvRepositoryEntity>?>.mapTolUseCaseEntity(): Result<List<ArrudeiaUseCaseEntity>?> {
        return when (this) {
            is Result.Success -> {
                var listResult = mutableListOf<ArrudeiaUseCaseEntity>()
                this.result?.forEach {
                    listResult.add(
                        ArrudeiaUseCaseEntity(
                            imageUrl = it.image_url
                        )
                    )
                }
                Result.Success(listResult)
            }
            else -> Result.Failure(Exception())
        }
    }

}
