package com.arrudeia.core.data.repository

import com.arrudeia.core.data.model.RecentSearchQuery
import com.arrudeia.core.data.model.asExternalModel
import com.arrudeia.core.database.dao.RecentSearchQueryDao
import com.arrudeia.core.database.model.RecentSearchQueryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import javax.inject.Inject

class DefaultRecentSearchRepository @Inject constructor(
    private val recentSearchQueryDao: RecentSearchQueryDao,
) : RecentSearchRepository {
    override suspend fun insertOrReplaceRecentSearch(searchQuery: String) {
        recentSearchQueryDao.insertOrReplaceRecentSearchQuery(
            RecentSearchQueryEntity(
                query = searchQuery,
                queriedDate = Clock.System.now(),
            ),
        )
    }

    override fun getRecentSearchQueries(limit: Int): Flow<List<RecentSearchQuery>> =
        recentSearchQueryDao.getRecentSearchQueryEntities(limit).map { searchQueries ->
            searchQueries.map {
                it.asExternalModel()
            }
        }

    override suspend fun clearRecentSearches() = recentSearchQueryDao.clearRecentSearchQueries()
}
