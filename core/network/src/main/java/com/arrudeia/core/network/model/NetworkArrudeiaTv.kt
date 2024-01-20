package com.arrudeia.core.network.model

import com.arrudeia.core.model.data.Topic
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Network representation of [Topic]
 */
@Serializable
data class NetworkArrudeiaTv(
    @SerializedName("image_url")
    val imageUrl: String = "",
)
