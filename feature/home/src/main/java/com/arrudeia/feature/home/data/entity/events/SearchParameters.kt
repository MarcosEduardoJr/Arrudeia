package com.arrudeia.feature.home.data.entity.events

data class SearchParameters(
    val engine: String,
    val gl: String,
    val hl: String,
    val q: String
)