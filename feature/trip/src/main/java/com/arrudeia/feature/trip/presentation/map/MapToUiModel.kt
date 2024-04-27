package com.arrudeia.feature.trip.presentation.map

import com.arrudeia.feature.trip.domain.entity.TravelUseCaseEntity
import com.arrudeia.feature.trip.presentation.model.TripUIModel

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
            coverImageUrl = it.coverImageUrl,
            whatsapp = it.whatsapp,
            description = it.description,
            include = it.include,
            optional = it.optional
        )

    }
    return listResult
}