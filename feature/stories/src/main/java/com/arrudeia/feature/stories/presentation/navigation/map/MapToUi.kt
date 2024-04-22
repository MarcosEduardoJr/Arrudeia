package com.arrudeia.feature.stories.navigation.map

import com.arrudeia.feature.stories.domain.entity.StoryUseCaseEntity
import com.arrudeia.feature.stories.presentation.model.StoriesUIModel

fun List<StoryUseCaseEntity>?.mapStoriesToUiModel(): List<StoriesUIModel> {
    val listResult = mutableListOf<StoriesUIModel>()
    this?.forEach {
        listResult.add(
            StoriesUIModel(
                image = it.image
            )
        )
    }
    return listResult
}