package com.arrudeia.core.network.model

import com.google.gson.annotations.SerializedName

data class ArrudeiaNetworkError(
    @SerializedName("message")
    val httpMessage: String?,
    val httpStatus: Int?
)