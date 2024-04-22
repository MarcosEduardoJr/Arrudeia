package com.arrudeia.feature.sign.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.feature.sign.presentation.ui.SignRoute
import com.arrudeia.navigation.signRoute

fun NavGraphBuilder.signScreen(
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = signRoute) {
        SignRoute(onRouteClick, onShowSnackbar)
    }
}
