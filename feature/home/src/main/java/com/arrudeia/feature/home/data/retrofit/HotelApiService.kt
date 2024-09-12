package com.arrudeia.feature.home.data.retrofit

import com.arrudeia.core.common.BuildConfig
import com.arrudeia.feature.home.data.entity.hotel.HotelDetailResponse
import com.arrudeia.feature.home.data.entity.hotel.HotelSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HotelApiService {

    @GET("search.json")
    suspend fun searchHotels(
        @Query("engine") engine: String,
        @Query("q") query: String,
        @Query("check_in_date") checkInDate: String,
        @Query("check_out_date") checkOutDate: String,
        @Query("adults") adults: Int,
        @Query("currency") currency: String,
        @Query("gl") gl: String,
        @Query("hl") hl: String,
        @Query("api_key") apiKey: String,
        @Query("children") children: Int,
        @Query("next_page_token") nextPageToken: String,
        @Query("children_ages") childrenAges: String,
    ): HotelSearchResponse

    @GET("search.json")
    suspend fun fetchHotelDetail(
        @Query("engine") engine: String,
        @Query("q") query: String,
        @Query("check_in_date") checkInDate: String,
        @Query("check_out_date") checkOutDate: String,
        @Query("adults") adults: Int,
        @Query("children") children: Int,
        @Query("children_ages") childrenAges: String,
        @Query("currency") currency: String,
        @Query("gl") gl: String,
        @Query("hl") hl: String,
        @Query("api_key") apiKey: String,
        @Query("property_token") propertyToken: String
    ): HotelDetailResponse

}