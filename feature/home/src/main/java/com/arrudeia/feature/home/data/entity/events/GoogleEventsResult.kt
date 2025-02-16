package com.arrudeia.feature.home.data.entity.events

data class GoogleEventsResult(
    val address: List<String>? = listOf(),
    val date: Date?,
    val description: String = "",
    val image: String? = "",
    val link: String = "",
    val thumbnail: String = "",
    val ticket_info: List<TicketInfo>? = listOf(),
    val title: String = "",
    val venue: Venue?
)