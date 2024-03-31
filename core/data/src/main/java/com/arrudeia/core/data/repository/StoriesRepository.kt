package com.arrudeia.core.data.repository
import com.arrudeia.core.data.entity.StoriesRepositoryEntity
import com.arrudeia.core.data.entity.StoryRepositoryEntity

/**
 * Interface representing network calls to the Arrudeia backend
 */
interface StoriesRepository {
   suspend fun getStories(): List<StoriesRepositoryEntity>?

    suspend fun getStoriesById(id : Long): List<StoryRepositoryEntity>?

}
