package com.arrudeia.feature.arrudeia.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.feature.arrudeia.presentation.ui.arrudeiaRoute
import com.arrudeia.core.data.navigation.arrudeiaRoute

fun NavGraphBuilder.arrudeiaScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = arrudeiaRoute) {
        arrudeiaRoute(
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}




