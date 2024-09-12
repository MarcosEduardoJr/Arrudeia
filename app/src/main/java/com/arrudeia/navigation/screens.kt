package com.arrudeia.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.arrudeia.feature.aid.presentation.navigation.aidDetailScreen
import com.arrudeia.feature.aid.presentation.navigation.aidScreen
import com.arrudeia.feature.arrudeia.presentation.navigation.arrudeiaScreen
import com.arrudeia.feature.checklist.presentation.navigation.checkListScreen
import com.arrudeia.feature.home.presentation.navigation.homeScreen
import com.arrudeia.feature.home.presentation.navigation.hotelDetailScreen
import com.arrudeia.feature.onboarding.presentation.navigation.onboardingScreen
import com.arrudeia.feature.profile.presentation.navigation.profileScreen
import com.arrudeia.feature.receipt.presentation.navigation.receiptDetailScreen
import com.arrudeia.feature.receipt.presentation.navigation.receiptScreen
import com.arrudeia.feature.services.presentation.navigation.newServiceScreen
import com.arrudeia.feature.services.presentation.navigation.servicesScreen
import com.arrudeia.feature.sign.presentation.navigation.signScreen
import com.arrudeia.feature.stories.presentation.navigation.navigateToStories
import com.arrudeia.feature.trip.presentation.navigation.navigateToTripDetail
import com.arrudeia.activeRunScreen
import com.arrudeia.runOverviewScreen

fun NavGraphBuilder.screens(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    showBottomBar: (Boolean) -> Unit,
) {
    signScreen(
        onRouteClick = navController::navigateToRoute,
        onShowSnackbar = onShowSnackbar,
        showBottomBar = showBottomBar
    )
    onboardingScreen(
        onRouteClick = navController::navigateToRoute,
        showBottomBar = showBottomBar
    )
    homeScreen(
        onRouteClick = navController::navigateToRoute,
        onShowSnackbar = onShowSnackbar,
        showBottomBar = showBottomBar,
        onHotelDetailsClick = { navController.navigate(it) },
        onEventDetailsClick = { navController.navigate(it) }
    )
    profileScreen(
        onBackClick = { navController.popBackStack() },
        onRouteClick = navController::navigateToRoute,
        onShowSnackbar = onShowSnackbar,
    )
    arrudeiaScreen(
        onBackClick = { navController.popBackStack() },
        onShowSnackbar = onShowSnackbar,
        showBottomBar = showBottomBar
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
    aidScreen(
        onReceiptDetailClick = { navController.navigate(it) },
        onShowSnackbar = onShowSnackbar
    )
    aidDetailScreen(
        onShowSnackbar = onShowSnackbar,
        onBackClick = { navController.popBackStack() }
    )
    servicesScreen(
        serviceDetailNavigationClick = { navController.navigate(it) },
        onChatClick = { navController.navigate(it) },
        onShowSnackbar = onShowSnackbar,
        onNewServiceNavigationClick = { navController.navigate(it) },
        onProfilePersonalParamNavigationClick = { navController.navigate(it) }
    )
    newServiceScreen(
        onShowSnackbar = onShowSnackbar,
        onBackClick = { navController.popBackStack() },
        onProfilePersonalParamNavigationClick = { navController.navigate(it) }
    )
    runOverviewScreen(
        onActiveRunClick = navController::navigateToRoute,
        showBottomBar = showBottomBar
    )
    activeRunScreen(
        onBackClick = { navController.popBackStack() },
        showBottomBar = showBottomBar
    )
    hotelDetailScreen(
        onBackClick = { navController.popBackStack() },
        onShowSnackbar = onShowSnackbar,
    )
}



