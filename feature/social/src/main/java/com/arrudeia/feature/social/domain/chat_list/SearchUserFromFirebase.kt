package com.arrudeia.feature.social.domain.chat_list

import com.arrudeia.feature.social.data.chat.SocialChatListRepositoryImpl
import javax.inject.Inject

class SearchUserFromFirebase @Inject constructor(
    private val userListScreenRepository: SocialChatListRepositoryImpl
) {
    suspend operator fun invoke(userEmail: String) =
        userListScreenRepository.searchUserFromFirebase(userEmail)
}