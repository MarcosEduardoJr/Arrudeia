package com.arrudeia.core.data.repository
import com.arrudeia.core.data.interactions.Result
import com.arrudeia.core.network.model.NetworkArrudeiaTvListImages

/**
 * Interface representing network calls to the Arrudeia backend
 */
interface StoriesRepository {
   suspend fun getStories(): List<StoriesRepositoryEntity>?

    suspend fun getStoriesById(id : Long): List<StoryRepositoryEntity>?

}
