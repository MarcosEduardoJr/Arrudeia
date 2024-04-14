package com.droidmaster.arrudeia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.arrudeia.feature.arrudeia.presentation.navigation.arrudeiaScreen
import com.arrudeia.feature.home.navigation.homeGraph
import com.arrudeia.feature.home.navigation.homeScreen
import com.arrudeia.feature.onboarding.navigation.onboardingScreen
import com.arrudeia.feature.profile.navigation.profileAddressScreen
import com.arrudeia.feature.profile.navigation.profileGraph
import com.arrudeia.feature.profile.navigation.profilePersonalInformationScreen
import com.arrudeia.feature.profile.navigation.profileScreen
import com.arrudeia.feature.sign.navigation.signScreen
import com.arrudeia.feature.stories.navigation.navigateToStories
import com.arrudeia.feature.stories.navigation.storiesScreen
import com.arrudeia.feature.trip.navigation.navigateToTripDetail
import com.arrudeia.feature.trip.navigation.tripDetailScreen
import com.arrudeia.navigation.arrudeiaRoute
import com.arrudeia.navigation.homeRoute
import com.arrudeia.navigation.onboardingRoute
import com.arrudeia.navigation.profileRoute
import com.droidmaster.arrudeia.ui.ArrudeiaAppState
import java.net.URLEncoder

@Composable
fun ArrudeiaNavHost(
    appState: ArrudeiaAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = arrudeiaRoute,
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
            onTripDetailClick = navController::navigateToTripDetail,
            onShowSnackbar = onShowSnackbar
        )
        profileScreen(
            onBackClick = navController::popBackStack,
            onRouteClick = navController::navigateToRoute,
            onShowSnackbar = onShowSnackbar
        )
        arrudeiaScreen(
            onBackClick = navController::popBackStack,
            onRouteClick = navController::navigateToRoute,
            onShowSnackbar = onShowSnackbar
        )

        homeGraph(
            onStoriesClick = navController::navigateToStories,
            nestedGraphs = {
                profileScreen(
                    onBackClick = navController::popBackStack,
                    onRouteClick = navController::navigateToRoute,
                    onShowSnackbar = onShowSnackbar
                )
                storiesScreen(
                    onStoriesClick = navController::navigateToStories,
                    onBackClick = navController::popBackStack
                )
                tripDetailScreen(
                )
            },
            onBackClick = navController::popBackStack,
            onRouteClick = navController::navigateToRoute,
            onShowSnackbar = onShowSnackbar
        )
        profileGraph(
            onBackClick = navController::popBackStack,
            nestedGraphs = {
                profilePersonalInformationScreen(
                    onBackClick = navController::popBackStack,
                    onShowSnackbar = onShowSnackbar
                )
                profileAddressScreen(
                    onBackClick = navController::popBackStack,
                    onShowSnackbar = onShowSnackbar
                )
            },
            onShowSnackbar = onShowSnackbar
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
