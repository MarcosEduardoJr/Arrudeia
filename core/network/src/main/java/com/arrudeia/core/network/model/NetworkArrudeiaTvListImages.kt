package com.arrudeia.core.network.model

import com.arrudeia.core.model.data.Topic
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Network representation of [Topic]
 */
@Serializable
data class NetworkArrudeiaTvListImages(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("images")
    val images: List<NetworkArrudeiaTvImage>?,
)

@Serializable
data class NetworkArrudeiaTvImage(
    @SerializedName("image_url")
    val imageUrl: String = "",
)
