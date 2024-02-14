package com.arrudeia.core.domain

import com.arrudeia.core.data.network.StoriesRepositoryImpl
import com.arrudeia.core.data.repository.StoryRepositoryEntity
import com.arrudeia.core.entity.StoryUseCaseEntity
import javax.inject.Inject

class GetAllStoriesByIdUseCase @Inject constructor(
    private val repository: StoriesRepositoryImpl,
) {
    suspend operator fun invoke(id: Long): List<StoryUseCaseEntity>? =
        repository.getStoriesById(id).mapTolUseCaseEntity()

    private fun List<StoryRepositoryEntity>?.mapTolUseCaseEntity(): List<StoryUseCaseEntity>? {
        val result = mutableListOf<StoryUseCaseEntity>()
        this?.forEach {
            result.add(StoryUseCaseEntity(image = it.image_url))
        }
        return result
    }

}
