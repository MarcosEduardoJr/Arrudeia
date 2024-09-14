package com.arrudeia.feature.home.domain

import com.arrudeia.core.data.repository.MapKeysRepositoryImpl
import com.arrudeia.feature.home.data.GoogleEventRepository
import javax.inject.Inject

class SearchGoogleEventUseCase @Inject constructor(
    private val repository: GoogleEventRepository,
    private val repositoryMapLibKey: MapKeysRepositoryImpl
) {
    companion object {
        const val SERPAPI_KEY = "SERPAPI_KEY"
    }

    suspend operator fun invoke(
        query: String,
    ) = repository.searchEvents(
        query,
        currency = "BRL",
        gl = "br",
        hl = "pt-br",
        apiKey = repositoryMapLibKey.getLocalLibKeys()[SERPAPI_KEY].orEmpty(),
    )
}