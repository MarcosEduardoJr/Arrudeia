package com.arrudeia.feature.home.domain

import com.arrudeia.core.data.repository.MapKeysRepositoryImpl
import com.arrudeia.feature.home.data.HotelRepository
import javax.inject.Inject

class FetchHotelDetailUseCase @Inject constructor(
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
        apiKey = repositoryMapLibKey.getLocalLibKeys()[SERPAPI_KEY].orEmpty(),
        propertyToken
    )
}