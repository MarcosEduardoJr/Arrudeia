package com.arrudeia.feature.sign.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.core.data.navigation.signRoute
import com.arrudeia.feature.sign.presentation.ui.RegisterOnboardingScreen

fun NavGraphBuilder.signScreen(
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    showBottomBar: (Boolean) -> Unit,
) {

    composable(route = signRoute) {
        showBottomBar(false)
        RegisterOnboardingScreen(
            onRouteClick = onRouteClick,
            onShowSnackbar = onShowSnackbar,
        )
        //  SignRoute(onRouteClick, onShowSnackbar, showBottomBar = showBottomBar)
    }
}


fun NavGraphBuilder.registerOnboarding(
    onRouteClick: (String) -> Unit,
    showBottomBar: (Boolean) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<RegisterOnboardingParam> {
        showBottomBar(false)
        RegisterOnboardingScreen(
            onRouteClick = onRouteClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}