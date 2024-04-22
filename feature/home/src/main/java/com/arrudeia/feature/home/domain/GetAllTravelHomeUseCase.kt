package com.arrudeia.feature.home.domain

import com.arrudeia.feature.home.data.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.feature.home.data.entity.TravelRepositoryEntity
import com.arrudeia.feature.home.domain.entity.TravelUseCaseEntity
import javax.inject.Inject

class GetAllTravelHomeUseCase @Inject constructor(
    private val repository: DefaultHomeTravelsRepositoryImpl,
) {
    suspend operator fun invoke(): List<TravelUseCaseEntity> =
        repository.getAllTravels().mapToTravelUseCaseEntity()

    private fun List<TravelRepositoryEntity>?.mapToTravelUseCaseEntity(): List<TravelUseCaseEntity> {
        var listResult = mutableListOf<TravelUseCaseEntity>()
        this?.forEach {
            listResult.add(
                TravelUseCaseEntity(
                    id = it.id,
                    name = it.name,
                    city = it.city,
                    state = it.state,
                    day = it.day,
                    month = it.month,
                    year = it.year,
                    price = it.price,
                    discount = it.discount,
                    cover_image_url = it.cover_image_url,
                    whatsapp = it.whatsapp,
                    description = it.description
                )
            )
        }
        return listResult
    }

}
