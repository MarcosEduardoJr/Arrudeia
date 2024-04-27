package com.arrudeia.feature.stories.presentation.navigation.map

import com.arrudeia.core.result.Result
import com.arrudeia.feature.stories.domain.entity.StoryUseCaseEntity
import com.arrudeia.feature.stories.presentation.model.StoriesUIModel

fun Result<List<StoryUseCaseEntity>?>.mapStoriesToUiModel(): List<StoriesUIModel> {
    val result = mutableListOf<StoriesUIModel>()
    when (val repository = this) {
        is Result.Success -> {
            repository.data?.forEach {
                result.add(StoriesUIModel(image = it.image))
            }
        }
        is Result.Error, is Result.Loading -> return listOf()
    }
    return result
}