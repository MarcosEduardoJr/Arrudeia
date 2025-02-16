package com.arrudeia.feature.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.arrudeia.core.data.navigation.HOME_GRAPH_ROUTE_PATTERN
import com.arrudeia.core.data.navigation.homeRoute
import com.arrudeia.core.data.navigation.profileRoute
import com.arrudeia.feature.home.presentation.navigation.param.EventDetailParam
import com.arrudeia.feature.home.presentation.navigation.param.HotelDetailParam
import com.arrudeia.feature.home.presentation.ui.events.detail.EventDetailRoute
import com.arrudeia.feature.home.presentation.ui.homeRoute
import com.arrudeia.feature.home.presentation.ui.hotels.detail.HotelDetailRoute
import com.arrudeia.feature.profile.presentation.ui.profile.profileRoute


fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    showBottomBar: (Boolean) -> Unit,
    onHotelDetailsClick: (HotelDetailParam) -> Unit,
    onEventDetailsClick: (EventDetailParam) -> Unit
) {

    composable(route = homeRoute) {
        homeRoute(
            onRouteClick,
            onShowSnackbar = onShowSnackbar,
            showBottomBar = showBottomBar,
            onHotelDetailsClick,
            onEventDetailsClick
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
        startDestination = profileRoute,
    ) {

        composable(route = profileRoute) {
            profileRoute(onBackClick, onRouteClick, onShowSnackbar = onShowSnackbar)
        }
        nestedGraphs()
    }
}


fun NavGraphBuilder.hotelDetailScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<HotelDetailParam> {
        val args = it.toRoute<HotelDetailParam>()
        HotelDetailRoute(
            onBackClick = onBackClick,
            args.query,
            args.checkInDate,
            args.checkOutDate,
            args.adults,
            args.children,
            args.childrenAges,
            args.propertyToken,
            onShowSnackbar = onShowSnackbar,
            amenities = args.amenities
        )
    }

    composable<EventDetailParam> {
        val args = it.toRoute<EventDetailParam>()
        EventDetailRoute(
            onBackClick = onBackClick,
            event = args.getParam()
        )
    }
}