package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.chatScreen

import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.repository.ChatScreenRepository

class InsertMessageToFirebase(
    private val chatScreenRepository: ChatScreenRepository
) {
    suspend operator fun invoke(
        chatRoomUUID: String,
        messageContent: String,
        registerUUID: String,
    ) = chatScreenRepository.insertMessageToFirebase(
        chatRoomUUID,
        messageContent,
        registerUUID,
    )
}