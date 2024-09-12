package com.arrudeia.feature.home.data.entity.hotel

data class HotelDetailSearchMetadata(
    val created_at: String,
    val google_hotels_url: String,
    val id: String,
    val json_endpoint: String,
    val prettify_html_file: String,
    val processed_at: String,
    val raw_html_file: String,
    val status: String,
    val total_time_taken: Double
)