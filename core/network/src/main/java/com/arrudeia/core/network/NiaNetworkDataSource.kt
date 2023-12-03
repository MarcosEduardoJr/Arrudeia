package com.arrudeia.core.network

import com.arrudeia.core.network.model.NetworkChangeList
import com.arrudeia.core.network.model.NetworkNewsResource
import com.arrudeia.core.network.model.NetworkTopic

/**
 * Interface representing network calls to the NIA backend
 */
interface NiaNetworkDataSource {
    suspend fun getTopics(ids: List<String>? = null): List<NetworkTopic>

    suspend fun getNewsResources(ids: List<String>? = null): List<NetworkNewsResource>

    suspend fun getTopicChangeList(after: Int? = null): List<NetworkChangeList>

    suspend fun getNewsResourceChangeList(after: Int? = null): List<NetworkChangeList>
}
