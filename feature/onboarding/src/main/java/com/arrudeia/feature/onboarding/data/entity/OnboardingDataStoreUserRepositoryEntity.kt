package com.arrudeia.feature.onboarding.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class OnboardingDataStoreUserRepositoryEntity(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var image: String = "",
)
