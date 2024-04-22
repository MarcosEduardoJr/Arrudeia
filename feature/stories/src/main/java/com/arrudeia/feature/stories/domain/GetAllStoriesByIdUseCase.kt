package com.arrudeia.feature.stories.domain

import com.arrudeia.feature.stories.data.StoriesRepositoryImpl
import com.arrudeia.feature.stories.data.entity.StoryRepositoryEntity
import com.arrudeia.feature.stories.domain.entity.StoryUseCaseEntity
import javax.inject.Inject

class GetAllStoriesByIdUseCase @Inject constructor(
    private val repository: StoriesRepositoryImpl,
) {
    suspend operator fun invoke(id: Long): List<StoryUseCaseEntity> =
        repository.getStoriesById(id).mapTolUseCaseEntity()

    private fun List<StoryRepositoryEntity>.mapTolUseCaseEntity(): List<StoryUseCaseEntity> {
        val result = mutableListOf<StoryUseCaseEntity>()
        this.forEach {
            result.add(StoryUseCaseEntity(image = it.image_url))
        }
        return result
    }

}
