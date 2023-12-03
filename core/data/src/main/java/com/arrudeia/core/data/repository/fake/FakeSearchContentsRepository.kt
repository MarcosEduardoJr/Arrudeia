package com.arrudeia.core.data.repository.fake

import com.arrudeia.core.data.repository.SearchContentsRepository
import com.arrudeia.core.model.data.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * Fake implementation of the [SearchContentsRepository]
 */
class FakeSearchContentsRepository @Inject constructor() : SearchContentsRepository {

    override suspend fun populateFtsData() { /* no-op */ }
    override fun searchContents(searchQuery: String): Flow<SearchResult> = flowOf()
    override fun getSearchContentsCount(): Flow<Int> = flowOf(1)
}
