package com.arrudeia.feature.arrudeia.data.entity

import com.arrudeia.core.graphql.GetArrudeiaPlaceQuery
import com.arrudeia.core.graphql.GetArrudeiaPlacesQuery

fun List<GetArrudeiaPlacesQuery.ArrudeiaPlace?>?.toEntity(): List<ArrudeiaPlaceRepositoryEntity>? {
    return if (this == null) null
    else {
        val list = mutableListOf<ArrudeiaPlaceRepositoryEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    val listAvaliable = mutableListOf<AvailableRepositoryEntity>()
                    item.available?.forEach { itemAvaliable ->
                        listAvaliable.add(
                            AvailableRepositoryEntity(
                                itemAvaliable?.id.orEmpty(),
                                itemAvaliable?.name.orEmpty(),
                                itemAvaliable?.placeId.orEmpty()
                            )
                        )
                    }

                    list.add(
                        ArrudeiaPlaceRepositoryEntity(
                            available = listAvaliable,
                            categoryName = item.categoryName.orEmpty(),
                            description = item.description.orEmpty(),
                            image = item.image.orEmpty(),
                            latitude = item.latitude,
                            longitude = item.longitude,
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


fun GetArrudeiaPlaceQuery.ArrudeiaPlace?.toEntity(): ArrudeiaPlaceRepositoryEntity? {
        var arrudeiaPlace: ArrudeiaPlaceRepositoryEntity? = null
        this?.let { item ->
            val listAvaliable = mutableListOf<AvailableRepositoryEntity>()
            item.available?.forEach { itemAvaliable ->
                listAvaliable.add(
                    AvailableRepositoryEntity(
                        itemAvaliable?.id.orEmpty(),
                        itemAvaliable?.name.orEmpty(),
                        itemAvaliable?.placeId.orEmpty()
                    )
                )
            }

            arrudeiaPlace = ArrudeiaPlaceRepositoryEntity(
                available = listAvaliable,
                categoryName = item.categoryName.orEmpty(),
                description = item.description.orEmpty(),
                image = item.image.orEmpty(),
                latitude = item.latitude,
                longitude = item.longitude,
                name = item.name.orEmpty(),
                phone = item.phone.orEmpty(),
                priceLevel = item.priceLevel,
                rating = item.rating,
                socialNetwork = item.socialNetwork.orEmpty(),
                subCategoryName = item.subCategoryName.orEmpty(),
                uuid = item.uuid.orEmpty()
            )

        }
        return arrudeiaPlace
}