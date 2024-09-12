package com.arrudeia.feature.home.data.entity.events

data class GoogleEventResponse(
    val events_results: List<GoogleEventsResult>,
    val search_information: SearchInformation,
    val search_metadata: SearchMetadata,
    val search_parameters: SearchParameters
)