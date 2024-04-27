package com.arrudeia.feature.profile.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.arrudeia.feature.profile.presentation.ui.profileAddressRoute
import com.arrudeia.feature.profile.presentation.ui.profilePersonalInformationRoute
import com.arrudeia.feature.profile.presentation.ui.profileRoute
import com.arrudeia.core.data.navigation.PROFILE_GRAPH_ROUTE_PATTERN
import com.arrudeia.core.data.navigation.profileAddressRoute
import com.arrudeia.core.data.navigation.profilePersonalInformationRoute
import com.arrudeia.core.data.navigation.profileRoute

fun NavGraphBuilder.profileScreen(
    onRouteClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = profileRoute) {
        profileRoute(
            onRouteClick = onRouteClick,
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar
        )
    }
}

fun NavGraphBuilder.profilePersonalInformationScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(
        route = profilePersonalInformationRoute,
    ) {
        profilePersonalInformationRoute(onBackClick = onBackClick, onShowSnackbar = onShowSnackbar)
    }
}

fun NavGraphBuilder.profileAddressScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(
        route = profileAddressRoute,
    ) {
        profileAddressRoute(onBackClick = onBackClick, onShowSnackbar = onShowSnackbar)
    }
}


fun NavGraphBuilder.profileGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    navigation(
        route = PROFILE_GRAPH_ROUTE_PATTERN,
        startDestination = profilePersonalInformationRoute,
    ) {
        composable(route = profilePersonalInformationRoute) {
            profilePersonalInformationRoute(onBackClick, onShowSnackbar = onShowSnackbar)
        }
        composable(route = profileAddressRoute) {
            profileAddressRoute(onBackClick, onShowSnackbar = onShowSnackbar)
        }
        nestedGraphs()
    }
}



