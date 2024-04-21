package com.arrudeia.feature.home.map

import com.arrudeia.core.entity.ArrudeiaUseCaseEntity
import com.arrudeia.core.entity.TravelUseCaseEntity
import com.arrudeia.feature.home.model.ArrudeiaTvUIModel
import com.arrudeia.feature.home.model.TravelUIModel

fun List<TravelUseCaseEntity>?.mapTravelsToUiModel(): List<TravelUIModel> {
    val listResult = mutableListOf<TravelUIModel>()
    this?.forEach {
        listResult.add(
            TravelUIModel(
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
                whatsapp = it.whatsapp
            )
        )
    }
    return listResult
}

fun List<ArrudeiaUseCaseEntity>?.mapArrTvToUiModel(): List<ArrudeiaTvUIModel> {
    val listResult = mutableListOf<ArrudeiaTvUIModel>()
    this?.forEach {
        listResult.add(
            ArrudeiaTvUIModel(
                id = it.id,
                imageUrl = it.imageUrl
            )
        )
    }
    return listResult
}