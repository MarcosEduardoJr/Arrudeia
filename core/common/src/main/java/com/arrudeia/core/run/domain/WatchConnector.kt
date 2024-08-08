package com.arrudeia.core.run.domain

import com.arrudeia.core.designsystem.component.util.util.EmptyResult
import com.arrudeia.core.run.domain.messaging.MessagingAction
import com.arrudeia.core.run.domain.messaging.MessagingError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WatchConnector {
    val connectedDevice: StateFlow<DeviceNode?>
    val messagingActions: Flow<MessagingAction>

    suspend fun sendActionToWatch(action: MessagingAction): EmptyResult<MessagingError>
    fun setIsTrackable(isTrackable: Boolean)
}