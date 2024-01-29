package com.arrudeia.core.network.model

import com.arrudeia.core.model.data.Topic
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Network representation of [Topic]
 */
@Serializable
data class NetworkArrudeiaTv(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image_url")
    val imageUrl: String = "",
)
