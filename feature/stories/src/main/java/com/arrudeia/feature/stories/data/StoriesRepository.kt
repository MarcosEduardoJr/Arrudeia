package com.arrudeia.feature.stories.data

import com.arrudeia.feature.stories.data.entity.StoriesRepositoryEntity
import com.arrudeia.feature.stories.data.entity.StoryRepositoryEntity

/**
 * Interface representing network calls to the Arrudeia backend
 */
interface StoriesRepository {
   suspend fun getStories(): List<StoriesRepositoryEntity>?

    suspend fun getStoriesById(id : Long): List<StoryRepositoryEntity>?

}
