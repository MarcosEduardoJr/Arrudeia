package com.arrudeia.feature.social.domain

import com.arrudeia.feature.social.data.SocialChatScreenRepositoryImpl
import javax.inject.Inject

class LoadMessageFromFirebase @Inject constructor(
    private val chatScreenRepository: SocialChatScreenRepositoryImpl
) {
    suspend operator fun invoke(
        chatRoomUUID: String,
        opponentUUID: String,
        registerUUID: String
    ) = chatScreenRepository.loadMessagesFromFirebase(chatRoomUUID, opponentUUID, registerUUID)
}