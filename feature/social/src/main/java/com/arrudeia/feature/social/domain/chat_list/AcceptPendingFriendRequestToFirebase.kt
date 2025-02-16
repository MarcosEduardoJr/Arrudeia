package com.arrudeia.feature.social.domain.chat_list

import com.arrudeia.feature.social.data.chat.SocialChatListRepositoryImpl
import javax.inject.Inject

class AcceptPendingFriendRequestToFirebase @Inject constructor(
    private val userListScreenRepository: SocialChatListRepositoryImpl
) {
    suspend operator fun invoke(registerUUID: String) =
        userListScreenRepository.acceptPendingFriendRequestToFirebase(registerUUID)
}