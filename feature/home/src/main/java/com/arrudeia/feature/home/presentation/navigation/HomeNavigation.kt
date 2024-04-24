package com.arrudeia.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.arrudeia.feature.home.HomeRoute
import com.arrudeia.feature.profile.ui.ProfileRoute
import com.arrudeia.feature.stories.StoriesRoute
import com.arrudeia.core.data.navigation.HOME_GRAPH_ROUTE_PATTERN
import com.arrudeia.core.data.navigation.homeRoute
import com.arrudeia.core.data.navigation.profileRoute
import com.arrudeia.core.data.navigation.storiesRoute


fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onRouteClick: (String) -> Unit,
    onStoriesClick: (String) -> Unit,
    onTripDetailClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {

    composable(route = homeRoute) {
        HomeRoute(onRouteClick, onStoriesClick =onStoriesClick, onTripDetailClick = onTripDetailClick,onShowSnackbar = onShowSnackbar)
    }
}


fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(HOME_GRAPH_ROUTE_PATTERN, navOptions)
}

fun NavGraphBuilder.homeGraph(
    onStoriesClick: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
    onBackClick: () -> Unit,
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    navigation(
        route = HOME_GRAPH_ROUTE_PATTERN,
        startDestination = storiesRoute,
    ) {
        composable(route = storiesRoute) {
            StoriesRoute(onStoriesClick, onBackClick)
        }
        composable(route = profileRoute) {
            ProfileRoute(onBackClick, onRouteClick,onShowSnackbar = onShowSnackbar)
        }
        nestedGraphs()
    }
}