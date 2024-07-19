package com.arrudeia.feature.home.presentation.navigation.param

import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaPlaceDetailsUiModel
import com.google.gson.Gson
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailParam(
    var placeDetail: String = ""
) {
    fun getParam(): ArrudeiaPlaceDetailsUiModel {
        return Gson().fromJson(placeDetail, ArrudeiaPlaceDetailsUiModel::class.java)
    }

    fun setParam(arrudeiaPlaceDetailsUiModel: ArrudeiaPlaceDetailsUiModel) {
        placeDetail = Gson().toJson(arrudeiaPlaceDetailsUiModel)
    }
}