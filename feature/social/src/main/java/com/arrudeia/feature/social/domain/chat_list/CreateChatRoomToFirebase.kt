package com.arrudeia.feature.social.domain.chat_list

import com.arrudeia.feature.social.data.chat.SocialChatListRepositoryImpl
import javax.inject.Inject

class CreateChatRoomToFirebase @Inject constructor(
    private val userListScreenRepository: SocialChatListRepositoryImpl
) {
    suspend operator fun invoke(acceptorUUID: String ) =
        userListScreenRepository.createChatRoomToFirebase(acceptorUUID )
}