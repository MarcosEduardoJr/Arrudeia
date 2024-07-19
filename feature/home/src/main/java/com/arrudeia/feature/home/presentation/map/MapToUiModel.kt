package com.arrudeia.feature.home.presentation.map

import com.arrudeia.feature.home.domain.entity.ArrudeiaUseCaseEntity
import com.arrudeia.feature.home.domain.entity.StatesByCountryUseCaseEntity
import com.arrudeia.feature.home.domain.entity.TravelUseCaseEntity
import com.arrudeia.feature.home.presentation.model.ArrudeiaTvUIModel
import com.arrudeia.feature.home.presentation.model.StateUIModel
import com.arrudeia.feature.home.presentation.model.TravelUIModel

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
                coverImageUrl = it.coverImageUrl,
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

fun List<StatesByCountryUseCaseEntity>?.mapStateToUiModel(): List<StateUIModel> {
    val listResult = mutableListOf<StateUIModel>()
    this?.forEach {
        listResult.add(
            StateUIModel(
                name = it.name
            )
        )
    }
    return listResult
}