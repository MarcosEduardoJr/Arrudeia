package com.arrudeia.feature.sign.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.feature.sign.OnboardingRoute
import com.arrudeia.navigation.onboardingRoute


fun NavGraphBuilder.onboardingScreen(
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = onboardingRoute) {
        OnboardingRoute( onRouteClick, onShowSnackbar)
    }
}
