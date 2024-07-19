package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen

import com.example.chatwithme.domain.repository.UserListScreenRepository

class RejectPendingFriendRequestToFirebase(
    private val userListScreenRepository: UserListScreenRepository
) {
    suspend operator fun invoke(registerUUID: String) =
        userListScreenRepository.rejectPendingFriendRequestToFirebase(registerUUID)
}