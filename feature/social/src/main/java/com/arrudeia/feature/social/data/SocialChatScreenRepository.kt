package com.arrudeia.feature.social.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.social.domain.chat_list.model.ChatMessage
import com.arrudeia.feature.social.domain.chat_list.model.User
import kotlinx.coroutines.flow.Flow

interface SocialChatScreenRepository {
    suspend fun insertMessageToFirebase(
        chatRoomUUID: String,
        messageContent: String,
        registerUUID: String,
    ): Flow<Result<Boolean>>

    suspend fun loadMessagesFromFirebase(
        chatRoomUUID: String,
        opponentUUID: String,
        registerUUID: String
    ): Flow<Result<List<ChatMessage>>>

    suspend fun loadOpponentProfileFromFirebase(opponentUUID: String): Flow<Result<User>>
    suspend fun blockFriendToFirebase(registerUUID: String): Flow<Result<Boolean>>
}