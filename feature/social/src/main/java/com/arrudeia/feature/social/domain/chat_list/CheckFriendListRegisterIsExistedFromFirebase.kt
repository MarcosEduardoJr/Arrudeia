package com.arrudeia.feature.social.domain.chat_list

import com.arrudeia.feature.social.data.chat.SocialChatListRepositoryImpl
import javax.inject.Inject

class CheckFriendListRegisterIsExistedFromFirebase @Inject constructor(
    private val userListScreenRepository: SocialChatListRepositoryImpl
) {
    suspend operator fun invoke(
        acceptorEmail: String,
        acceptorUUID: String
    ) =
        userListScreenRepository.checkFriendListRegisterIsExistedFromFirebase(
            acceptorEmail = acceptorEmail,
            acceptorUUID = acceptorUUID
        )
}