package com.arrudeia.feature.home.domain

import com.arrudeia.core.common.BuildConfig
import com.arrudeia.feature.home.data.GoogleEventRepository
import com.arrudeia.feature.home.data.HotelRepository
import javax.inject.Inject

class SearchGoogleEventUseCase @Inject constructor(
    private val repository: GoogleEventRepository
) {
    suspend operator fun invoke(
        query: String,
    ) = repository.searchEvents(
        query,
        currency = "BRL",
        gl = "br",
        hl = "pt-br",
    )
}