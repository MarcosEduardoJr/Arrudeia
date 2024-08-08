package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.model

import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.model.ChatMessage

data class FriendListRow(
    val chatRoomUUID: String,
    val userEmail: String = "",
    val userUUID: String = "",
    val oneSignalUserId: String,
    val registerUUID: String = "",
    val userPictureUrl: String = "",
    val lastMessage: ChatMessage = ChatMessage()
)