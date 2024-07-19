package com.arrudeia.core.run.domain.messaging

import com.arrudeia.core.designsystem.component.util.util.Error
enum class MessagingError: Error {
    CONNECTION_INTERRUPTED,
    DISCONNECTED,
    UNKNOWN
}