package com.arrudeia.core.run.data

import com.arrudeia.core.designsystem.component.util.util.EmptyResult
import com.arrudeia.core.run.connectivity.data.WearNodeDiscovery
import com.arrudeia.core.run.connectivity.data.messaging.WearMessagingClient
import com.arrudeia.core.run.domain.DeviceNode
import com.arrudeia.core.run.domain.DeviceType
import com.arrudeia.core.run.domain.NodeDiscovery
import com.arrudeia.core.run.domain.messaging.MessagingAction
import com.arrudeia.core.run.domain.messaging.MessagingClient
import com.arrudeia.core.run.domain.messaging.MessagingError
import com.arrudeia.core.run.domain.wear.PhoneConnector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class WatchToPhoneConnector @Inject constructor (
    nodeDiscovery: WearNodeDiscovery,
    applicationScope: CoroutineScope,
    private val messagingClient: WearMessagingClient
): PhoneConnector {

    private val _connectedNode = MutableStateFlow<DeviceNode?>(null)
    override val connectedNode = _connectedNode.asStateFlow()

    override val messagingActions = nodeDiscovery
        .observeConnectedDevices(DeviceType.WATCH)
        .flatMapLatest { connectedNodes ->
            val node = connectedNodes.firstOrNull()
            if(node != null && node.isNearby) {
                _connectedNode.value = node
                messagingClient.connectToNode(node.id)
            } else flowOf()
        }
        .shareIn(
            applicationScope,
            SharingStarted.Eagerly
        )

    override suspend fun sendActionToPhone(action: MessagingAction): EmptyResult<MessagingError> {
        return messagingClient.sendOrQueueAction(action)
    }
}