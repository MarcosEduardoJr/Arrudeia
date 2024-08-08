package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.chatScreen

import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.repository.ChatScreenRepository

class BlockFriendToFirebase(
    private val chatScreenRepository: ChatScreenRepository
) {
    suspend operator fun invoke(registerUUID: String) =
        chatScreenRepository.blockFriendToFirebase(registerUUID)
}