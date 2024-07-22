package com.arrudeia.feature.stories.domain

import com.arrudeia.core.common.R.string.generic_error
import com.arrudeia.core.result.Result
import com.arrudeia.feature.stories.data.StoriesRepositoryImpl
import com.arrudeia.feature.stories.data.entity.StoryRepositoryEntity
import com.arrudeia.feature.stories.domain.entity.StoryUseCaseEntity
import javax.inject.Inject

class GetAllStoriesByIdUseCase @Inject constructor(
    private val repository: StoriesRepositoryImpl,
) {
    suspend operator fun invoke(id: Long): Result<List<StoryUseCaseEntity>?> =
        repository.getStoriesById(id).mapTolUseCaseEntity()

    private fun Result<List<StoryRepositoryEntity>?>.mapTolUseCaseEntity(): Result<List<StoryUseCaseEntity>?> {
        val result = mutableListOf<StoryUseCaseEntity>()
        when (val repository = this) {
            is Result.Success -> {
                repository.data?.forEach {
                    result.add(StoryUseCaseEntity(image = it.image_url))
                }
            }

            is Result.Error, is Result.Loading -> return Result.Error(generic_error)
        }

        return Result.Success(result)
    }

}
