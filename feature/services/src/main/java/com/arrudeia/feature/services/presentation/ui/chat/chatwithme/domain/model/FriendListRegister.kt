package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.model

import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.model.ChatMessage

data class FriendListRegister(
    var chatRoomUUID: String = "",
    var registerUUID: String = "",
    var requesterEmail: String = "",
    var requesterUUID: String = "",
    var acceptorEmail: String = "",
    var acceptorUUID: String = "",
    var status: String = "",
    var lastMessage: ChatMessage = ChatMessage()
)