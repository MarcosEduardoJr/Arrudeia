package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.authScreen

import com.example.chatwithme.domain.repository.AuthScreenRepository

class IsUserAuthenticatedInFirebase(
    private val authScreenRepository: AuthScreenRepository
) {
    operator fun invoke() = authScreenRepository.isUserAuthenticatedInFirebase()
}