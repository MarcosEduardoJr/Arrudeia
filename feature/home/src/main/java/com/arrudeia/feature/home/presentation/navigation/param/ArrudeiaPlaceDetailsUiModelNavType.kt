package com.arrudeia.feature.home.presentation.navigation.param

import android.os.Bundle
import androidx.navigation.NavType
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaPlaceDetailsUiModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ArrudeiaPlaceDetailsUiModelNavType : NavType<ArrudeiaPlaceDetailsUiModel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): ArrudeiaPlaceDetailsUiModel? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): ArrudeiaPlaceDetailsUiModel {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: ArrudeiaPlaceDetailsUiModel) {
        bundle.putString(key, Json.encodeToString(value))
    }
}