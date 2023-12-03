package com.arrudeia.core.testing.repository

import com.arrudeia.core.data.model.RecentSearchQuery
import com.arrudeia.core.data.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestRecentSearchRepository : RecentSearchRepository {

    private val cachedRecentSearches: MutableList<RecentSearchQuery> = mutableListOf()

    override fun getRecentSearchQueries(limit: Int): Flow<List<RecentSearchQuery>> =
        flowOf(cachedRecentSearches.sortedByDescending { it.queriedDate }.take(limit))

    override suspend fun insertOrReplaceRecentSearch(searchQuery: String) {
        cachedRecentSearches.add(RecentSearchQuery(searchQuery))
    }

    override suspend fun clearRecentSearches() {
        cachedRecentSearches.clear()
    }
}
