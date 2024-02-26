package com.droidmaster.arrudeia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.arrudeia.feature.home.navigation.homeGraph
import com.arrudeia.feature.home.navigation.homeScreen
import com.arrudeia.feature.onboarding.navigation.onboardingScreen
import com.arrudeia.feature.sign.navigation.signScreen
import com.arrudeia.feature.stories.navigation.navigateToStories
import com.arrudeia.feature.stories.navigation.storiesScreen
import com.arrudeia.feature.trip.navigation.navigateToTripDetail
import com.arrudeia.feature.trip.navigation.tripDetailScreen
import com.arrudeia.navigation.homeRoute
import com.arrudeia.navigation.onboardingRoute
import com.droidmaster.arrudeia.ui.ArrudeiaAppState
import java.net.URLEncoder

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun ArrudeiaNavHost(
    appState: ArrudeiaAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = onboardingRoute,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        signScreen(
            onRouteClick = navController::navigateToRoute,
            onShowSnackbar = onShowSnackbar,
        )
        onboardingScreen(
            onRouteClick = navController::navigateToRoute
        )
        homeScreen(
            onRouteClick = navController::navigateToRoute,
            onStoriesClick = navController::navigateToStories,
            onTripDetailClick = navController::navigateToTripDetail
        )
        homeGraph(
            onStoriesClick = navController::navigateToStories,
            nestedGraphs = {
                storiesScreen(
                    onStoriesClick = navController::navigateToStories,
                    onBackClick = navController::popBackStack
                )
                tripDetailScreen(
                )
            },
            onBackClick = navController::popBackStack
        )
    }
}

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()
fun NavController.navigateToRoute(topicId: String) {
    val encodedId = URLEncoder.encode(topicId, URL_CHARACTER_ENCODING)
    this.navigate(encodedId) {
        launchSingleTop = true
    }
}
