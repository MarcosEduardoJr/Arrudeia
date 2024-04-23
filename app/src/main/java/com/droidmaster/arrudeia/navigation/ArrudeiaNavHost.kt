package com.droidmaster.arrudeia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.arrudeia.feature.arrudeia.presentation.navigation.arrudeiaScreen
import com.arrudeia.feature.home.navigation.homeGraph
import com.arrudeia.feature.home.navigation.homeScreen
import com.arrudeia.feature.onboarding.presentation.navigation.onboardingScreen
import com.arrudeia.feature.profile.presentation.navigation.profileAddressScreen
import com.arrudeia.feature.profile.presentation.navigation.profileGraph
import com.arrudeia.feature.profile.presentation.navigation.profilePersonalInformationScreen
import com.arrudeia.feature.profile.presentation.navigation.profileScreen
import com.arrudeia.feature.sign.presentation.navigation.signScreen
import com.arrudeia.feature.stories.navigation.navigateToStories
import com.arrudeia.feature.stories.navigation.storiesScreen
import com.arrudeia.feature.trip.presentation.navigation.navigateToTripDetail
import com.arrudeia.feature.trip.presentation.navigation.tripDetailScreen
import com.arrudeia.navigation.onboardingRoute
import com.droidmaster.arrudeia.ui.ArrudeiaAppState
import java.net.URLEncoder

@Composable
fun arrudeiaNavHost(
    appState: ArrudeiaAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = onboardingRoute,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        screens(navController, onShowSnackbar)
        homeGraph(navController, onShowSnackbar)
        profileGraph(navController, onShowSnackbar)
    }
}

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()
fun NavController.navigateToRoute(topicId: String) {
    val encodedId = URLEncoder.encode(topicId, URL_CHARACTER_ENCODING)
    this.navigate(encodedId) {
        launchSingleTop = true
    }
}
