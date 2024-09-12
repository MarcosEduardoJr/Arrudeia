package com.arrudeia.feature.home.data.retrofit

import com.arrudeia.core.common.BuildConfig
import com.arrudeia.feature.home.data.entity.events.GoogleEventResponse
import com.arrudeia.feature.home.data.entity.hotel.HotelDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleEventApiService {
    companion object{
        val GOOGLE_EVENTS = "google_events"
    }

    @GET("search.json")
    suspend fun searchEvents(
        @Query("engine") engine: String? = GOOGLE_EVENTS,
        @Query("q") query: String,
        @Query("currency") currency: String,
        @Query("gl") gl: String,
        @Query("hl") hl: String,
        @Query("api_key") apiKey: String? = BuildConfig.SERPAPI_KEY,
    ): GoogleEventResponse
}