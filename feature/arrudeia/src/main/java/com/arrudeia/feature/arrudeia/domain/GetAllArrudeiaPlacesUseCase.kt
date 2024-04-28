package com.arrudeia.feature.arrudeia.domain

import com.arrudeia.feature.arrudeia.data.ArrudeiaPlaceRepositoryImpl
import com.arrudeia.feature.arrudeia.data.entity.ArrudeiaPlaceRepositoryEntity
import com.arrudeia.feature.arrudeia.domain.entity.ArrudeiaAvailablePlaceUseCaseEntity
import com.arrudeia.feature.arrudeia.domain.entity.ArrudeiaPlaceDetailsUseCaseEntity
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import com.arrudeia.core.result.Result

class GetAllArrudeiaPlacesUseCase @Inject constructor(
    private val repository: ArrudeiaPlaceRepositoryImpl
) {
    suspend operator fun invoke(): Result<List<ArrudeiaPlaceDetailsUseCaseEntity>?> =
        repository.getArrudeiaPlaces().toEntity()

    private fun Result<List<ArrudeiaPlaceRepositoryEntity>?>.toEntity():
            Result<List<ArrudeiaPlaceDetailsUseCaseEntity>?> {
        return when (this) {
            is Result.Success -> {
                val list = mutableListOf<ArrudeiaPlaceDetailsUseCaseEntity>()
                this.data.let { place ->
                    place?.forEach { item ->
                        item.let {
                            val listAvaliable = mutableListOf<ArrudeiaAvailablePlaceUseCaseEntity>()
                            item.available?.forEach { itemAvaliable ->
                                listAvaliable.add(
                                    ArrudeiaAvailablePlaceUseCaseEntity(
                                        itemAvaliable.name.orEmpty(),
                                    )
                                )
                            }

                            list.add(
                                ArrudeiaPlaceDetailsUseCaseEntity(
                                    available = listAvaliable,
                                    categoryName = item.categoryName.orEmpty(),
                                    description = item.description.orEmpty(),
                                    image = item.image.orEmpty(),
                                    location = if (it.latitude != null && it.longitude != null) LatLng(
                                        it.latitude,
                                        it.longitude
                                    ) else null,
                                    name = item.name.orEmpty(),
                                    phone = item.phone.orEmpty(),
                                    priceLevel = item.priceLevel,
                                    rating = item.rating,
                                    socialNetwork = item.socialNetwork.orEmpty(),
                                    subCategoryName = item.subCategoryName.orEmpty(),
                                    uuid = item.uuid.orEmpty()
                                )
                            )
                        }
                    }
                }
                Result.Success(list.toList())
            }

            is Result.Error -> {
                Result.Error(null)
            }

            else -> {
                Result.Error(null)
            }
        }
    }


}
