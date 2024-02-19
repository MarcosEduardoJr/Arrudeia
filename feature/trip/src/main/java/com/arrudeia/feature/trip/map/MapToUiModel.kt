package com.arrudeia.feature.trip.map

import com.arrudeia.core.entity.TravelUseCaseEntity
import com.arrudeia.feature.trip.model.TripUIModel

 fun TravelUseCaseEntity?.mapTravelToUiModel(): TripUIModel? {
    var listResult: TripUIModel? = null
    this?.let {
        listResult = TripUIModel(
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
    return listResult
}