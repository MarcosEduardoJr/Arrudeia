package com.arrudeia.feature.home.domain

import com.arrudeia.feature.home.data.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.feature.home.data.entity.ArrudeiaTvRepositoryEntity
import com.arrudeia.feature.home.data.entity.StateRepositoryEntity
import com.arrudeia.feature.home.domain.entity.ArrudeiaUseCaseEntity
import com.arrudeia.feature.home.domain.entity.StatesByCountryUseCaseEntity
import javax.inject.Inject

class GetAllStatesByCountryUseCase @Inject constructor(
    private val repository: DefaultHomeTravelsRepositoryImpl,
) {
    suspend operator fun invoke(country : String) =
        repository.getStates(country).mapTolUseCaseEntity()

    private fun List<StateRepositoryEntity>.mapTolUseCaseEntity(): List<StatesByCountryUseCaseEntity> {
        var listResult = mutableListOf<StatesByCountryUseCaseEntity>()
        this?.forEach {
            listResult.add(
                StatesByCountryUseCaseEntity(
                    name = it.name
                )
            )
        }
        return listResult
    }

}
