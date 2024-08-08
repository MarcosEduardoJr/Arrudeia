package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.model

data class ChatMessage(
    val profileUUID: String = "",
    var message: String = "",
    var date: Long = 0,
    var status: String = ""
)
