package com.arrudeia.core.run.domain.messaging

import com.arrudeia.core.designsystem.component.util.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface MessagingClient {
    fun connectToNode(nodeId: String): Flow<MessagingAction>
    suspend fun sendOrQueueAction(action: MessagingAction): EmptyResult<MessagingError>
}