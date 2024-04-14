package com.arrudeia.feature.arrudeia.domain

import com.arrudeia.feature.arrudeia.data.ArrudeiaPlaceRepositoryImpl
import com.arrudeia.feature.arrudeia.data.entity.ArrudeiaPlaceRepositoryEntity
import com.arrudeia.feature.arrudeia.domain.entity.ArrudeiaAvailablePlaceUseCaseEntity
import com.arrudeia.feature.arrudeia.domain.entity.ArrudeiaPlaceDetailsUseCaseEntity
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetAllArrudeiaPlacesUseCase @Inject constructor(
    private val repository: ArrudeiaPlaceRepositoryImpl
) {

    suspend operator fun invoke(): List<ArrudeiaPlaceDetailsUseCaseEntity>? =
        repository.getArrudeiaPlaces().toEntity()

    fun List<ArrudeiaPlaceRepositoryEntity>?.toEntity(): List<ArrudeiaPlaceDetailsUseCaseEntity>? {
        return if (this == null) null
        else {
            val list = mutableListOf<ArrudeiaPlaceDetailsUseCaseEntity>()
            this.let { place ->
                place.forEach { item ->
                    item?.let {
                        val listAvaliable = mutableListOf<ArrudeiaAvailablePlaceUseCaseEntity>()
                        item.available?.forEach { itemAvaliable ->
                            listAvaliable.add(
                                ArrudeiaAvailablePlaceUseCaseEntity(
                                    itemAvaliable?.name.orEmpty(),
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
            list.toList()
        }
    }


}
