package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.authScreen

import com.example.chatwithme.domain.repository.AuthScreenRepository

class SignUp(
    private val authScreenRepository: AuthScreenRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        authScreenRepository.signUp(email, password)
}