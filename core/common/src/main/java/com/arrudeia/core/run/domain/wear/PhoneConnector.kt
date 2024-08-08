package com.arrudeia.core.run.domain.wear

import com.arrudeia.core.designsystem.component.util.util.EmptyResult
import com.arrudeia.core.run.domain.DeviceNode
import com.arrudeia.core.run.domain.messaging.MessagingAction
import com.arrudeia.core.run.domain.messaging.MessagingError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PhoneConnector {
    val connectedNode: StateFlow<DeviceNode?>
    val messagingActions: Flow<MessagingAction>

    suspend fun sendActionToPhone(action: MessagingAction): EmptyResult<MessagingError>
}