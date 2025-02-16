package com.arrudeia.feature.home.data.entity.hotel

data class HotelDetailTotalRate(
    val before_taxes_fees: String,
    val extracted_before_taxes_fees: Int,
    val extracted_lowest: Int,
    val lowest: String?
)