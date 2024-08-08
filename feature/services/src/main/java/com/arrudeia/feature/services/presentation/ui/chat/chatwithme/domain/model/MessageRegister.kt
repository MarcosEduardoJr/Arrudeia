package com.example.chatwithme.domain.model

import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.model.ChatMessage

data class MessageRegister(
    var chatMessage: ChatMessage,
    var isMessageFromOpponent: Boolean
)