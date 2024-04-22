package com.arrudeia.feature.home.domain

import com.arrudeia.feature.home.data.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.feature.home.data.entity.ArrudeiaTvRepositoryEntity
import com.arrudeia.feature.home.domain.entity.ArrudeiaUseCaseEntity
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
