package com.arrudeia.core.domain

import com.arrudeia.core.data.network.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.core.data.entity.TravelRepositoryEntity
import com.arrudeia.core.entity.TravelUseCaseEntity
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
