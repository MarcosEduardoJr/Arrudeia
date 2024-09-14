package com.arrudeia.feature.home.data

import com.arrudeia.feature.home.data.entity.hotel.HotelDetailResponse
import com.arrudeia.feature.home.data.entity.hotel.HotelSearchResponse
import com.arrudeia.feature.home.data.retrofit.HotelApiService
import javax.inject.Inject

class HotelRepository @Inject constructor(
    private val apiService: HotelApiService
) {
    suspend fun searchHotels(
        engine: String,
        query: String,
        checkInDate: String,
        checkOutDate: String,
        adults: Int,
        currency: String,
        gl: String,
        hl: String,
        apiKey: String,
        children: Int,
        nextPageToken: String,
        childrenAges: String,
    ): HotelSearchResponse {
        return apiService.searchHotels(
            engine,
            query,
            checkInDate,
            checkOutDate,
            adults,
            currency,
            gl,
            hl,
            apiKey = apiKey,
            children,
            nextPageToken,
            childrenAges
        )
    }

    suspend fun fetchHotelDetail(
        engine: String,
        query: String,
        checkInDate: String,
        checkOutDate: String,
        adults: Int,
        children: Int,
        childrenAges: String,
        currency: String,
        gl: String,
        hl: String,
        apiKey: String,
        propertyToken: String
    ): HotelDetailResponse {
        return apiService.fetchHotelDetail(
            engine,
            query,
            checkInDate,
            checkOutDate,
            adults,
            children,
            childrenAges,
            currency,
            gl,
            hl,
            apiKey,
            propertyToken
        )
    }
}