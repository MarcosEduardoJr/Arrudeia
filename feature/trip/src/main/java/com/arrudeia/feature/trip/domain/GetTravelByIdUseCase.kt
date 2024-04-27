package com.arrudeia.feature.trip.domain

import com.arrudeia.feature.trip.data.TripTravelRepositoryImpl
import com.arrudeia.feature.trip.data.entity.TravelRepositoryEntity
import com.arrudeia.feature.trip.domain.entity.TravelUseCaseEntity
import javax.inject.Inject
import com.arrudeia.core.result.Result
import com.arrudeia.feature.trip.R

class GetTravelByIdUseCase @Inject constructor(
    private val repository: TripTravelRepositoryImpl,
) {
    suspend operator fun invoke(id: Long):
            Result<TravelUseCaseEntity?> =
        repository.getTravelById(id).mapToTravelUseCaseEntity()

    private fun Result<TravelRepositoryEntity?>.mapToTravelUseCaseEntity(): Result<TravelUseCaseEntity?> {
        var item: Result<TravelUseCaseEntity?> = Result.Error(R.string.erro_message_list_travels)
        when (this) {
            is Result.Success -> {
                Result.Success(this.data?.let {
                    item = Result.Success(
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
                            coverImageUrl = it.cover_image_url,
                            whatsapp = it.whatsapp,
                            description = it.description,
                            include = it.include,
                            optional = it.optional
                        )
                    )
                })
            }

            is Result.Error -> {
                item = Result.Error(this.message)
            }

            else -> {
                item = Result.Error(R.string.erro_message_list_travels)
            }
        }


        return item
    }

}
