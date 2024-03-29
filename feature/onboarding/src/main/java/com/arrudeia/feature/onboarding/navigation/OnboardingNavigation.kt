package com.arrudeia.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.feature.onboarding.OnboardingRoute
import com.arrudeia.navigation.onboardingRoute


fun NavGraphBuilder.onboardingScreen(
    onRouteClick: (String) -> Unit,
) {
    composable(route = onboardingRoute) {
        OnboardingRoute( onRouteClick)
    }
}
