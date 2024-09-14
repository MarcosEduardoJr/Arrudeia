package com.arrudeia.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.arrudeia.feature.home.presentation.navigation.homeGraph
import com.arrudeia.feature.profile.presentation.navigation.profileScreen
import com.arrudeia.feature.trip.presentation.navigation.tripDetailScreen

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    homeGraph(
        nestedGraphs = {
            profileScreen(
                onBackClick = { navController.popBackStack() },
                onRouteClick = navController::navigateToRoute,
                onShowSnackbar = onShowSnackbar
            )
            tripDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        },
        onBackClick = { navController.popBackStack() },
        onRouteClick = navController::navigateToRoute,
        onShowSnackbar = onShowSnackbar
    )
}

