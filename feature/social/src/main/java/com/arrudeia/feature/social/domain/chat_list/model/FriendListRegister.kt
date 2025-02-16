package com.arrudeia.feature.social.domain.chat_list.model

data class FriendListRegister(
    var chatRoomUUID: String = "",
    var registerUUID: String = "",
    var requesterEmail: String = "",
    var requesterUUID: String = "",
    var acceptorEmail: String = "",
    var acceptorUUID: String = "",
    var status: String = "",
    var lastMessage: ChatMessage = ChatMessage(),
)