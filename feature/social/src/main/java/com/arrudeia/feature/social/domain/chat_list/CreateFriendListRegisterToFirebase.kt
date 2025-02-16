package com.arrudeia.feature.social.domain.chat_list

import com.arrudeia.core.data.repository.FirebaseUserRepositoryImpl
import com.arrudeia.feature.social.data.chat.SocialChatListRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class CreateFriendListRegisterToFirebase @Inject constructor(
    private val userListScreenRepository: SocialChatListRepositoryImpl,
    private val userImageFirebaseRepository: FirebaseUserRepositoryImpl,
    private val firebaseAuth: FirebaseAuth,
) {
    suspend operator fun invoke(
        chatRoomUUID: String,
        acceptorEmail: String,
        acceptorUUID: String,
        acceptorName : String,
        requesterName : String
    ) = userListScreenRepository.createFriendListRegisterToFirebase(
        chatRoomUUID,
        acceptorEmail,
        acceptorUUID,
        acceptorName,
        requesterName
    )
}