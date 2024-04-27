package com.arrudeia.feature.stories.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class StoriesRepositoryEntity(
    val id: Long = 0,
    val images: List<StoryRepositoryEntity>? = listOf(),
)

@Serializable
@Suppress("ConstructorParameterNaming")
data class StoryRepositoryEntity(
    val image_url: String = "",
)
