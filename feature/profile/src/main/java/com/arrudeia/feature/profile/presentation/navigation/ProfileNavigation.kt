package com.arrudeia.feature.profile.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.arrudeia.core.data.navigation.PROFILE_GRAPH_ROUTE_PATTERN
import com.arrudeia.core.data.navigation.profileAddressRoute
import com.arrudeia.core.data.navigation.profileInterestRoute
import com.arrudeia.core.data.navigation.profilePersonalInformationRoute
import com.arrudeia.core.data.navigation.profileRoute
import com.arrudeia.core.profile.param.ProfilePersonalParam
import com.arrudeia.feature.profile.presentation.ui.address.profileAddressRoute
import com.arrudeia.feature.profile.presentation.ui.interest.ProfileInterestRoute
import com.arrudeia.feature.profile.presentation.ui.personal.profilePersonalInformationRoute
import com.arrudeia.feature.profile.presentation.ui.profile.profileRoute

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
    showBottomBar: (Boolean) -> Unit,
) {
    composable<ProfilePersonalParam>(
    ) {
        profilePersonalInformationRoute(
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar,
            showBottomBar = showBottomBar
        )
    }
}

fun NavGraphBuilder.profileAddressScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = profileAddressRoute) {
        profileAddressRoute(onBackClick = onBackClick, onShowSnackbar = onShowSnackbar)
    }
}


fun NavGraphBuilder.profileGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    showBottomBar: (Boolean) -> Unit,
) {
    navigation(
        route = PROFILE_GRAPH_ROUTE_PATTERN,
        startDestination = profilePersonalInformationRoute,
    ) {
        composable(route = profilePersonalInformationRoute) {
            profilePersonalInformationRoute(
                onBackClick,
                onShowSnackbar = onShowSnackbar,
                showBottomBar = showBottomBar
            )
        }
        composable(route = profileAddressRoute) {
            profileAddressRoute(onBackClick, onShowSnackbar = onShowSnackbar)
        }

        composable(route = profileInterestRoute) {
            ProfileInterestRoute(onBackClick, onShowSnackbar = onShowSnackbar)
        }

        nestedGraphs()
    }
}



