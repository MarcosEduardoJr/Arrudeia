package com.arrudeia.feature.onboarding.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.feature.onboarding.presentation.ui.onboardingRoute
import com.arrudeia.core.data.navigation.onboardingRoute


fun NavGraphBuilder.onboardingScreen(
    onRouteClick: (String) -> Unit,
) {
    composable(route = onboardingRoute) {
        onboardingRoute( onRouteClick)
    }
}
