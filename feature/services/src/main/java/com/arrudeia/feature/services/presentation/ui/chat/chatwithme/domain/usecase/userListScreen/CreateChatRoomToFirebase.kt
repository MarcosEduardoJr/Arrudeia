package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen

import com.example.chatwithme.domain.repository.UserListScreenRepository

class CreateChatRoomToFirebase(
    private val userListScreenRepository: UserListScreenRepository
) {
    suspend operator fun invoke(acceptorUUID: String) =
        userListScreenRepository.createChatRoomToFirebase(acceptorUUID)
}