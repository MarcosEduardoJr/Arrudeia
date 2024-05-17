package com.droidmaster.arrudeia.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.arrudeia.core.data.navigation.aidRoute
import com.arrudeia.core.data.navigation.arrudeiaRoute
import com.arrudeia.core.data.navigation.checkListRoute
import com.arrudeia.core.data.navigation.homeRoute
import com.arrudeia.core.data.navigation.profileRoute
import com.arrudeia.core.data.navigation.receiptRoute
import com.arrudeia.feature.home.presentation.navigation.navigateToHome
import com.droidmaster.arrudeia.navigation.TopLevelDestination
import com.droidmaster.arrudeia.navigation.navigateToRoute
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberArrudeiaAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): ArrudeiaAppState {
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {
        ArrudeiaAppState(
            navController,
            coroutineScope,
            windowSizeClass
        )
    }
}

@Stable
class ArrudeiaAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination


    val shouldShowBottomBar: Boolean =
        windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar


    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigate(homeRoute)
            TopLevelDestination.PROFILE -> navController.navigate(profileRoute)
            TopLevelDestination.CHECKLIST -> navController.navigate(checkListRoute)
            TopLevelDestination.RECEIPT -> navController.navigate(receiptRoute)
            TopLevelDestination.AID -> navController.navigate(aidRoute)
        }
    }
}
