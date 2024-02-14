package com.arrudeia.core.domain

import com.arrudeia.core.data.interactions.Result
import com.arrudeia.core.data.network.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.core.data.repository.TravelRepositoryEntity
import com.arrudeia.core.entity.TravelUseCaseEntity
import javax.inject.Inject

class GetTravelByIdUseCase @Inject constructor(
    private val repository: DefaultHomeTravelsRepositoryImpl,
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
                description = it.description
            )
        }
        return result
    }

}
