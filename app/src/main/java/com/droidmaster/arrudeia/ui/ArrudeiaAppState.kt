package com.droidmaster.arrudeia.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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








    fun navigateToSearch() {
        //  navController.navigateToSearch()
    }
}

/**
 * Stores information about navigation events to be used with JankStats
 */

