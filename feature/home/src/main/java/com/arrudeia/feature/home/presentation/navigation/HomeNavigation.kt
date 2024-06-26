package com.arrudeia.feature.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.arrudeia.feature.home.presentation.ui.homeRoute
import com.arrudeia.feature.profile.presentation.ui.profile.profileRoute
import com.arrudeia.feature.stories.presentation.ui.storiesRoute
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
        homeRoute(
            onRouteClick,
            onStoriesClick = onStoriesClick,
            onTripDetailClick = onTripDetailClick,
            onShowSnackbar = onShowSnackbar
        )
    }
}


fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(HOME_GRAPH_ROUTE_PATTERN, navOptions)
}

fun NavGraphBuilder.homeGraph(
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
            storiesRoute(onBackClick)
        }
        composable(route = profileRoute) {
            profileRoute(onBackClick, onRouteClick, onShowSnackbar = onShowSnackbar)
        }
        nestedGraphs()
    }
}