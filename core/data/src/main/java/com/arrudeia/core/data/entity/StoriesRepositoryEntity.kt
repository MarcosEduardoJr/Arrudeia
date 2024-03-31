package com.arrudeia.core.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class StoriesRepositoryEntity(
    val id: Long = 0,
    val images: List<StoryRepositoryEntity>? = listOf(),
)

@Serializable
data class StoryRepositoryEntity(
    val image_url: String = "",
)
