package com.arrudeia.feature.home.data

import com.arrudeia.feature.home.data.entity.events.GoogleEventResponse
import com.arrudeia.feature.home.data.retrofit.GoogleEventApiService
import javax.inject.Inject

class GoogleEventRepository @Inject constructor(
    private val apiService: GoogleEventApiService
) {
    suspend fun searchEvents(
        query: String,
        currency: String,
        gl: String,
        hl: String,
        apiKey: String,
    ): GoogleEventResponse {
        return apiService.searchEvents(
            query = query,
            currency = currency,
            gl = gl,
            hl = hl,
            apiKey = apiKey,
        )
    }
}