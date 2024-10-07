package com.arrudeia.feature.social.domain

import com.arrudeia.feature.social.data.SocialChatScreenRepositoryImpl
import javax.inject.Inject

class InsertMessageToFirebase @Inject constructor(
    private val chatScreenRepository: SocialChatScreenRepositoryImpl
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