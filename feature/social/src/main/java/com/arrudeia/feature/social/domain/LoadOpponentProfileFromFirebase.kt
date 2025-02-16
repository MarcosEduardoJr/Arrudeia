package com.arrudeia.feature.social.domain

import com.arrudeia.feature.social.data.SocialChatScreenRepositoryImpl
import javax.inject.Inject

class LoadOpponentProfileFromFirebase @Inject constructor(
    private val chatScreenRepository: SocialChatScreenRepositoryImpl
) {
    suspend operator fun invoke(opponentUUID: String) =
        chatScreenRepository.loadOpponentProfileFromFirebase(opponentUUID)
}