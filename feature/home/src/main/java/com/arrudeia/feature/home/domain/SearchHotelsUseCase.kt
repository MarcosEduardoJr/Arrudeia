package com.arrudeia.feature.home.domain

import com.arrudeia.core.data.repository.MapKeysRepositoryImpl
import com.arrudeia.feature.home.data.HotelRepository
import com.arrudeia.feature.home.data.entity.hotel.HotelSearchResponse
import javax.inject.Inject

class SearchHotelsUseCase @Inject constructor(
    private val repository: HotelRepository,
    private val repositoryMapLibKey: MapKeysRepositoryImpl
) {
    companion object {
        const val SERPAPI_KEY = "SERPAPI_KEY"
    }

    suspend operator fun invoke(
        query: String,
        checkInDate: String,
        checkOutDate: String,
        adults: Int,
        children: Int,
        nextPageToken: String,
        childrenAges: String,
    ): HotelSearchResponse {
        val apikey = repositoryMapLibKey.getLocalLibKeys()[SERPAPI_KEY].orEmpty()
        return repository.searchHotels(
            engine = "google_hotels",
            query,
            checkInDate,
            checkOutDate,
            adults,
            currency = "BRL",
            gl = "br",
            hl = "pt-br",
            apiKey = apikey,
            children,
            nextPageToken,
            childrenAges
        )
    }
}