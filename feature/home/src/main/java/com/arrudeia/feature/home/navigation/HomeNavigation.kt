package com.arrudeia.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.arrudeia.feature.home.SignRoute
import com.arrudeia.navigation.homeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = homeRoute) {
        SignRoute(onRouteClick, onShowSnackbar)
    }
}
