package com.arrudeia.feature.trip.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.arrudeia.feature.trip.TripDetailRoute
import com.arrudeia.navigation.tripDetailRoute

fun NavController.navigateToTripDetail(navOptions: NavOptions? = null) {
    this.navigate(tripDetailRoute, navOptions)
}

fun NavGraphBuilder.tripDetailScreen(
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = tripDetailRoute) {
        TripDetailRoute(onRouteClick, onShowSnackbar)
    }
}
