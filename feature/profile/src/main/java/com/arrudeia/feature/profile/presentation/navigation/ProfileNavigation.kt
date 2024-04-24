package com.arrudeia.feature.profile.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.arrudeia.feature.profile.ui.ProfileAddressRoute
import com.arrudeia.feature.profile.ui.ProfilePersonalInformationRoute
import com.arrudeia.feature.profile.ui.ProfileRoute
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
        ProfileRoute(
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
        ProfilePersonalInformationRoute(onBackClick = onBackClick, onShowSnackbar = onShowSnackbar)
    }
}

fun NavGraphBuilder.profileAddressScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(
        route = profileAddressRoute,
    ) {
        ProfileAddressRoute(onBackClick = onBackClick, onShowSnackbar = onShowSnackbar)
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
            ProfilePersonalInformationRoute(onBackClick, onShowSnackbar = onShowSnackbar)
        }
        composable(route = profileAddressRoute) {
            ProfileAddressRoute(onBackClick, onShowSnackbar = onShowSnackbar)
        }
        nestedGraphs()
    }
}



