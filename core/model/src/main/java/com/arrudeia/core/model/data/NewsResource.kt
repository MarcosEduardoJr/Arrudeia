package com.arrudeia.core.model.data

import kotlinx.datetime.Instant

/**
 * External data layer representation of a fully populated Arrudeia news resource
 */
data class NewsResource(
    val id: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String?,
    val publishDate: Instant,
    val type: String,
    val topics: List<Topic>,
)
