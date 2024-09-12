package com.arrudeia.feature.home.domain

import com.arrudeia.core.common.BuildConfig
import com.arrudeia.feature.home.data.HotelRepository
import javax.inject.Inject

class FetchHotelDetailUseCase @Inject constructor(
    private val repository: HotelRepository
) {
    suspend operator fun invoke(
        query: String,
        checkInDate: String,
        checkOutDate: String,
        adults: Int,
        children: Int,
        childrenAges: String,
        propertyToken: String
    ) = repository.fetchHotelDetail(
        engine = "google_hotels",
        query,
        checkInDate,
        checkOutDate,
        adults,
        children,
        childrenAges,
        currency = "BRL",
        gl = "br",
        hl = "pt-br",
        apiKey = BuildConfig.SERPAPI_KEY,
        propertyToken
    )
}