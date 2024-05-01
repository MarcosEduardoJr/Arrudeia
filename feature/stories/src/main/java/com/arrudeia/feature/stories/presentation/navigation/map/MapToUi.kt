package com.arrudeia.feature.stories.presentation.navigation.map

import com.arrudeia.core.result.Result
import com.arrudeia.feature.stories.domain.entity.StoryUseCaseEntity
import com.arrudeia.feature.stories.presentation.model.StoriesUIModel

fun List<StoryUseCaseEntity>?.mapStoriesToUiModel(): List<StoriesUIModel> {
    val result = mutableListOf<StoriesUIModel>()
    this?.forEach {
        result.add(StoriesUIModel(image = it.image))
    }
    return result
}