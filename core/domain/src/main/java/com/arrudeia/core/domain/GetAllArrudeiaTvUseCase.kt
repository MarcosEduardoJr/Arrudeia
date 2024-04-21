package com.arrudeia.core.domain

import com.arrudeia.core.data.network.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.core.data.entity.ArrudeiaTvRepositoryEntity
import com.arrudeia.core.entity.ArrudeiaUseCaseEntity
import javax.inject.Inject

class GetAllArrudeiaTvUseCase @Inject constructor(
    private val repository: DefaultHomeTravelsRepositoryImpl,
) {
    suspend operator fun invoke(): List<ArrudeiaUseCaseEntity> =
        repository.getAllArrudeiaTv().mapTolUseCaseEntity()

    private fun List<ArrudeiaTvRepositoryEntity>.mapTolUseCaseEntity(): List<ArrudeiaUseCaseEntity> {
        var listResult = mutableListOf<ArrudeiaUseCaseEntity>()
        this?.forEach {
            listResult.add(
                ArrudeiaUseCaseEntity(
                    id = it.id,
                    imageUrl = it.image_url
                )
            )
        }
        return listResult
    }

}
