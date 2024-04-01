package com.arrudeia.feature.arrudeia.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.feature.arrudeia.ui.ArrudeiaRoute
import com.arrudeia.navigation.arrudeiaRoute

fun NavGraphBuilder.arrudeiaScreen(
    onRouteClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = arrudeiaRoute) {
        ArrudeiaRoute(
            onRouteClick = onRouteClick,
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}




