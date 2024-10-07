package com.arrudeia.feature.social.domain.chat_list

import com.arrudeia.feature.social.data.SocialChatScreenRepositoryImpl
import javax.inject.Inject

class BlockFriendToFirebase @Inject constructor(
    private val chatScreenRepository: SocialChatScreenRepositoryImpl
) {
    suspend operator fun invoke(registerUUID: String) =
        chatScreenRepository.blockFriendToFirebase(registerUUID)
}