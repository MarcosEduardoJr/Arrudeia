package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.profileScreen

import com.example.chatwithme.domain.repository.ProfileScreenRepository

class SignOut(
    private val profileScreenRepository: ProfileScreenRepository
) {
    suspend operator fun invoke() = profileScreenRepository.signOut()
}