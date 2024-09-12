package com.arrudeia.feature.home.domain

import com.arrudeia.core.common.BuildConfig
import com.arrudeia.feature.home.data.HotelRepository
import com.arrudeia.feature.home.data.entity.hotel.HotelSearchResponse
import javax.inject.Inject

class SearchHotelsUseCase @Inject constructor(
    private val repository: HotelRepository
) {
    suspend operator fun invoke(
        query: String,
        checkInDate: String,
        checkOutDate: String,
        adults: Int,
        children: Int,
        nextPageToken: String,
        childrenAges: String,
    ): HotelSearchResponse {
        return repository.searchHotels(
            engine = "google_hotels" ,
            query,
            checkInDate,
            checkOutDate,
            adults,
            currency = "BRL",
            gl= "br",
            hl = "pt-br",
            apiKey = BuildConfig.SERPAPI_KEY,
            children,
            nextPageToken,
            childrenAges
        )
    }
}