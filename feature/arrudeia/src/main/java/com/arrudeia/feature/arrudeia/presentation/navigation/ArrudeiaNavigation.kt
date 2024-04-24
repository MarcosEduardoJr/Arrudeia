package com.arrudeia.feature.arrudeia.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.feature.arrudeia.presentation.ui.ArrudeiaRoute
import com.arrudeia.core.data.navigation.arrudeiaRoute

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




