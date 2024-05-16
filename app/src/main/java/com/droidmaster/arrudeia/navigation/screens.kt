package com.droidmaster.arrudeia.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.arrudeia.feature.arrudeia.presentation.navigation.arrudeiaScreen
import com.arrudeia.feature.checklist.presentation.navigation.checkListScreen
import com.arrudeia.feature.home.presentation.navigation.homeScreen
import com.arrudeia.feature.onboarding.presentation.navigation.onboardingScreen
import com.arrudeia.feature.profile.presentation.navigation.profileScreen
import com.arrudeia.feature.receipt.presentation.navigation.param.ReceiptDetailParam
import com.arrudeia.feature.receipt.presentation.navigation.receiptDetailScreen
import com.arrudeia.feature.receipt.presentation.navigation.receiptScreen
import com.arrudeia.feature.sign.presentation.navigation.signScreen
import com.arrudeia.feature.stories.presentation.navigation.navigateToStories
import com.arrudeia.feature.trip.presentation.navigation.navigateToTripDetail

fun NavGraphBuilder.screens(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean
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
        onBackClick = { navController.popBackStack() },
        onRouteClick = navController::navigateToRoute,
        onShowSnackbar = onShowSnackbar
    )
    arrudeiaScreen(
        onBackClick = { navController.popBackStack() },
        onShowSnackbar = onShowSnackbar
    )
    checkListScreen(
        onBackClick = { navController.popBackStack() }
    )
    receiptScreen(
        onReceiptDetailClick = { navController.navigate(it) },
        onShowSnackbar = onShowSnackbar
    )
    receiptDetailScreen(
        onShowSnackbar = onShowSnackbar,
        onBackClick = { navController.popBackStack() }
    )
}



