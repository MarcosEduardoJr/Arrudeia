package com.droidmaster.arrudeia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.droidmaster.arrudeia.ui.ArrudeiaAppState
import com.arrudeia.feature.home.navigation.homeScreen
import com.arrudeia.feature.sign.navigation.onboardingScreen
import com.arrudeia.feature.trip.navigation.tripDetailScreen
import com.arrudeia.navigation.onboardingRoute
import com.arrudeia.feature.sign.navigation.signScreen
import com.arrudeia.navigation.homeRoute
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
    startDestination: String = homeRoute,
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
            onRouteClick = navController::navigateToRoute,
            onShowSnackbar = onShowSnackbar,
        )
        homeScreen(
            onRouteClick = navController::navigateToRoute,
            onShowSnackbar = onShowSnackbar,
        )
        tripDetailScreen(
            onRouteClick = navController::navigateToRoute,
            onShowSnackbar = onShowSnackbar,
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
