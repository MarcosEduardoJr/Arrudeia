package com.droidmaster.arrudeia.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.droidmaster.arrudeia.R
import com.droidmaster.arrudeia.navigation.ArrudeiaNavHost
import com.droidmaster.arrudeia.navigation.TopLevelDestination
import com.arrudeia.core.data.util.NetworkMonitor
import com.arrudeia.core.designsystem.component.ArrudeiaBackground
import com.arrudeia.core.designsystem.component.ArrudeiaGradientBackground
import com.arrudeia.core.designsystem.component.ArrudeiaNavigationBar
import com.arrudeia.core.designsystem.component.ArrudeiaNavigationBarItem
import com.arrudeia.core.designsystem.component.ArrudeiaNavigationRail
import com.arrudeia.core.designsystem.component.ArrudeiaNavigationRailItem
import com.arrudeia.core.designsystem.component.ArrudeiaTopAppBar
import com.arrudeia.core.designsystem.icon.ArrudeiaIcons
import com.arrudeia.core.designsystem.theme.GradientColors

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class,
)
@Composable
fun ArrudeiaApp(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    appState: ArrudeiaAppState = rememberArrudeiaAppState(
        networkMonitor = networkMonitor,
        windowSizeClass = windowSizeClass,
    ),
) {
    ArrudeiaBackground {
        ArrudeiaGradientBackground(
            gradientColors = GradientColors()
        ) {
            val snackbarHostState = remember { SnackbarHostState() }

            val isOffline by appState.isOffline.collectAsStateWithLifecycle()

            // If user is not connected to the internet show a snack bar to inform them.
            val notConnectedMessage = stringResource(R.string.app_name)
            LaunchedEffect(isOffline) {
                if (isOffline) {
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = Indefinite,
                    )
                }
            }


            Scaffold(
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                },
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                snackbarHost = { SnackbarHost(snackbarHostState) },
                bottomBar = {
                    if (false) {
                        ArrudeiaBottomBar(
                            destinations = appState.topLevelDestinations,
                            onNavigateToDestination = appState::navigateToTopLevelDestination,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier.testTag("NiaBottomBar"),
                        )
                    }
                },
            ) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    if (appState.shouldShowNavRail) {
                        ArrudeiaNavRail(
                            destinations = appState.topLevelDestinations,
                            onNavigateToDestination = appState::navigateToTopLevelDestination,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier
                                .testTag("NiaNavRail")
                                .safeDrawingPadding(),
                        )
                    }

                    Column(Modifier.fillMaxSize()) {
                        // Show the top app bar on top level destinations.
                        val destination = appState.currentTopLevelDestination
                        if (destination != null) {
                            ArrudeiaTopAppBar(
                                titleRes = destination.titleTextId,
                                navigationIcon = ArrudeiaIcons.Search,
                                navigationIconContentDescription = stringResource(
                                    id = R.string.app_name,
                                ),
                                actionIcon = ArrudeiaIcons.Settings,
                                actionIconContentDescription = stringResource(
                                    id = R.string.app_name,
                                ),
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = Color.Transparent,
                                ),
                                onActionClick = { },
                                onNavigationClick = { appState.navigateToSearch() },
                            )
                        }

                        ArrudeiaNavHost(appState = appState, onShowSnackbar = { message, action ->
                            snackbarHostState.showSnackbar(
                                message = message,
                                actionLabel = action,
                                duration = Short,
                            ) == ActionPerformed
                        })
                    }
                }
            }
        }
    }
}

@Composable
private fun ArrudeiaNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    ArrudeiaNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            ArrudeiaNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun ArrudeiaBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    ArrudeiaNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            ArrudeiaNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
