package com.arrudeia.feature.sign.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.feature.sign.SignRoute
import com.arrudeia.navigation.signRoute

fun NavGraphBuilder.signScreen(
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = signRoute) {
        SignRoute(onRouteClick, onShowSnackbar)
    }
}
