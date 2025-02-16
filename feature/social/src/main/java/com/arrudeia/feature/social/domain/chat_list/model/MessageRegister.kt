package com.arrudeia.feature.social.domain.chat_list.model

data class MessageRegister(
    var chatMessage: ChatMessage,
    var isMessageFromOpponent: Boolean
)