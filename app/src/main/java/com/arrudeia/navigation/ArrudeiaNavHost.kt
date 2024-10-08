package com.arrudeia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.arrudeia.core.data.navigation.socialRoute
import com.arrudeia.ui.ArrudeiaAppState
import java.net.URLEncoder

@Composable
fun arrudeiaNavHost(
    appState: ArrudeiaAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = socialRoute,
    showBottomBar: (Boolean) -> Unit,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        screens(navController, onShowSnackbar,showBottomBar)
        homeGraph(navController, onShowSnackbar)
        profileGraph(navController, onShowSnackbar, showBottomBar)
    }
}

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()
fun NavController.navigateToRoute(topicId: String) {
    val encodedId = URLEncoder.encode(topicId, URL_CHARACTER_ENCODING)
    this.navigate(encodedId) {
        launchSingleTop = true
    }
}
