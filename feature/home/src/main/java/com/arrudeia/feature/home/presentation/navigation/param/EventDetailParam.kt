package com.arrudeia.feature.home.presentation.navigation.param

import com.arrudeia.feature.home.data.entity.events.GoogleEventsResult
import com.google.gson.Gson
import kotlinx.serialization.Serializable

@Serializable
data class EventDetailParam(
    private var data: String = ""
) {
    fun getParam(): GoogleEventsResult {
        return Gson().fromJson(data, GoogleEventsResult::class.java)
    }

    fun setParam(param: GoogleEventsResult) {
        data = Gson().toJson(param)
    }
}