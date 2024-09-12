package com.arrudeia.feature.home.data.entity.hotel

data class SearchMetadata(
    val id: String?="",
    val status: String?="",
    val json_endpoint: String?="",
    val created_at: String?="",
    val processed_at: String?="",
    val google_hotels_url: String?="",
    val raw_html_file: String?="",
    val prettify_html_file: String?="",
    val total_time_taken: Double
)

data class SearchParameters(
    val engine: String?="",
    val q: String?="",
    val gl: String?="",
    val hl: String?="",
    val currency: String?="",
    val check_in_date: String?="",
    val check_out_date: String?="",
    val adults: Int?=0,
    val children: Int
)

data class SearchInformation(
    val total_results: Int
)

data class Brand(
    val id: Int?=0,
    val name: String?="",
    val children: List<Brand>?
)

data class GpsCoordinates(
    val latitude: Double?=0.0,
    val longitude: Double
)

data class RatePerNight(
    val lowest: String?="",
    val extracted_lowest: Int?=0,
    val before_taxes_fees: String?="",
    val extracted_before_taxes_fees: Int
)

data class TotalRate(
    val lowest: String?="",
)

data class Price(
    val source: String?="",
    val logo: String?="",
    val rate_per_night: RatePerNight
)

data class HotelDetailTransportation(
    val type: String?="",
    val duration: String
)

data class NearbyPlace(
    val name: String?="",
    val hotelDetailTransportations: List<HotelDetailTransportation>
)

data class HotelItemImage(
    val thumbnail: String?="",
    val original_image: String?="",
)

data class ReviewBreakdown(
    val name: String?="",
    val description: String?="",
    val total_mentioned: Int?=0,
    val positive: Int?=0,
    val negative: Int?=0,
    val neutral: Int?=0
)

data class Property(
    val type: String?="",
    val name: String?="",
    val description: String?="",
    val logo: String?="",
    val sponsored:Boolean?=false,
    val gps_coordinates: HotelDetailGpsCoordinates? = null,
    val check_in_time: String?="",
    val check_out_time: String?="",
    val rate_per_night: RatePerNight? = null,
    val total_rate: TotalRate? = null,
    val prices: List<HotelDetailPrice>? = listOf(),
    val nearby_places: List<HotelDetailNearbyPlace>? = listOf(),
    val hotel_class: String?="",
    val extracted_hotel_class: Int?=0,
    val images: List<HotelItemImage>? = listOf(),
    val overall_rating: Double?=0.0,
    val reviews: Int?=0,
    val location_rating: Double?=0.0,
    val reviews_breakdown: List<ReviewBreakdown>? = listOf(),
    val amenities: List<String>? = listOf(),
    val property_token: String?="",
    val serpapi_property_details_link: String?=""
)

data class SerpapiPagination(
    val current_from: Int?=0,
    val current_to: Int?=0,
    val next_page_token: String?="",
    val next: String
)

data class HotelSearchResponse(
    val search_metadata: HotelDetailSearchMetadata,
    val search_parameters: HotelDetailSearchParameters,
    val search_information: SearchInformation,
    val brands: List<Brand>? = listOf(),
    val properties: List<Property>? = listOf(),
    val serpapi_pagination: SerpapiPagination
)