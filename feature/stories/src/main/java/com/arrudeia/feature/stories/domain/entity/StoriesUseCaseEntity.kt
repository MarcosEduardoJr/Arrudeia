package com.arrudeia.feature.stories.domain.entity

import kotlinx.serialization.Serializable


@Serializable
data class StoriesUseCaseEntity(
    val id: Long = 0,
    val imageUrl: List<StoryUseCaseEntity>?,
)

@Serializable
data class StoryUseCaseEntity(
    val image: String = "",
)