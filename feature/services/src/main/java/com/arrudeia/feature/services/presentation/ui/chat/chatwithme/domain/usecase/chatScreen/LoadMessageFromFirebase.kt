package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.chatScreen

import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.repository.ChatScreenRepository

class LoadMessageFromFirebase(
    private val chatScreenRepository: ChatScreenRepository
) {
    suspend operator fun invoke(
        chatRoomUUID: String,
        opponentUUID: String,
        registerUUID: String
    ) = chatScreenRepository.loadMessagesFromFirebase(chatRoomUUID, opponentUUID, registerUUID)
}