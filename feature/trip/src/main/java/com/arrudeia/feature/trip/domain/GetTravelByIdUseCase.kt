package com.arrudeia.feature.trip.domain

import com.arrudeia.feature.trip.data.TripTravelRepositoryImpl
import com.arrudeia.feature.trip.data.entity.TravelRepositoryEntity
import com.arrudeia.feature.trip.domain.entity.TravelUseCaseEntity
import javax.inject.Inject

class GetTravelByIdUseCase @Inject constructor(
    private val repository: TripTravelRepositoryImpl,
) {
    suspend operator fun invoke(id: Long): TravelUseCaseEntity? =
        repository.getTravelById(id).mapToTravelUseCaseEntity()

    private fun TravelRepositoryEntity?.mapToTravelUseCaseEntity(): TravelUseCaseEntity? {
        var result: TravelUseCaseEntity? = null
        this?.let {
            result = TravelUseCaseEntity(
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
                description = it.description,
                include = it.include,
                optional = it.optional
            )
        }
        return result
    }

}
