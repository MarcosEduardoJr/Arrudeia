package com.arrudeia.core.data.model

import com.arrudeia.core.database.model.TopicEntity
import com.arrudeia.core.network.model.NetworkTopic

fun NetworkTopic.asEntity() = TopicEntity(
    id = id,
    name = name,
    shortDescription = shortDescription,
    longDescription = longDescription,
    url = url,
    imageUrl = imageUrl,
)
