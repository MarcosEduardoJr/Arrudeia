package com.arrudeia.core.testing.repository

import com.arrudeia.core.data.repository.SearchContentsRepository
import com.arrudeia.core.model.data.NewsResource
import com.arrudeia.core.model.data.SearchResult
import com.arrudeia.core.model.data.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class TestSearchContentsRepository : SearchContentsRepository {

    private val cachedTopics: MutableList<Topic> = mutableListOf()
    private val cachedNewsResources: MutableList<NewsResource> = mutableListOf()

    override suspend fun populateFtsData() { /* no-op */ }

    override fun searchContents(searchQuery: String): Flow<SearchResult> = flowOf(
        SearchResult(
            topics = cachedTopics.filter {
                it.name.contains(searchQuery) ||
                    it.shortDescription.contains(searchQuery) ||
                    it.longDescription.contains(searchQuery)
            },
            newsResources = cachedNewsResources.filter {
                it.content.contains(searchQuery) ||
                    it.title.contains(searchQuery)
            },
        ),
    )

    override fun getSearchContentsCount(): Flow<Int> = flow {
        emit(cachedTopics.size + cachedNewsResources.size)
    }

    /**
     * Test only method to add the topics to the stored list in memory
     */
    fun addTopics(topics: List<Topic>) {
        cachedTopics.addAll(topics)
    }

    /**
     * Test only method to add the news resources to the stored list in memory
     */
    fun addNewsResources(newsResources: List<NewsResource>) {
        cachedNewsResources.addAll(newsResources)
    }
}
