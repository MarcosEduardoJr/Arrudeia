package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.authScreen

data class AuthUseCases(
    val isUserAuthenticated: IsUserAuthenticatedInFirebase,
    val signIn: SignIn,
    val signUp: SignUp,
)