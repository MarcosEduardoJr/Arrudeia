package com.arrudeia.core.data.repository

import kotlinx.serialization.Serializable

@Serializable
data class StoriesRepositoryEntity(
    val id: Long = 0,
    val images: List<StoryRepositoryEntity>?,
)

@Serializable
data class StoryRepositoryEntity(
    val image_url: String = "",
)
