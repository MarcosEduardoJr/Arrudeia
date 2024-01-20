package com.arrudeia.core.network

import com.arrudeia.core.network.model.NetworkTravel
import kotlinx.coroutines.flow.Flow
/**
 * Interface representing network calls to the Arrudeia backend
 */
interface RetrofitNetworkDataSource {
    suspend fun getAllTravel(): List<NetworkTravel>
}
