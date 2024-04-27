package com.arrudeia.feature.stories.data

import com.arrudeia.feature.stories.data.entity.StoriesRepositoryEntity
import com.arrudeia.feature.stories.data.entity.StoryRepositoryEntity

import com.arrudeia.core.result.Result
interface StoriesRepository {
    suspend fun getStories(): Result<List<StoriesRepositoryEntity>?>

    suspend fun getStoriesById(id: Long): Result<List<StoryRepositoryEntity>?>

}
