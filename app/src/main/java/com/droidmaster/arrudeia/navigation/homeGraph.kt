package com.droidmaster.arrudeia.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.arrudeia.feature.home.navigation.homeGraph
import com.arrudeia.feature.profile.presentation.navigation.profileScreen
import com.arrudeia.feature.stories.navigation.navigateToStories
import com.arrudeia.feature.stories.navigation.storiesScreen
import com.arrudeia.feature.trip.presentation.navigation.tripDetailScreen

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    homeGraph(
        onStoriesClick = navController::navigateToStories,
        nestedGraphs = {
            profileScreen(
                onBackClick = { navController.popBackStack() },
                onRouteClick = navController::navigateToRoute,
                onShowSnackbar = onShowSnackbar
            )
            storiesScreen(
                onStoriesClick = navController::navigateToStories,
                onBackClick = { navController.popBackStack() }
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

