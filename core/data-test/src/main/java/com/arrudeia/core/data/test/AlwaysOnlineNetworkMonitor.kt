package com.arrudeia.core.data.test

import com.arrudeia.core.data.util.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AlwaysOnlineNetworkMonitor @Inject constructor() : NetworkMonitor {
    override val isOnline: Flow<Boolean> = flowOf(true)
}
