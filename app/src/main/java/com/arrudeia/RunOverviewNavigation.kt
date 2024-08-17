package com.arrudeia

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.arrudeia.MainActivity
import com.arrudeia.core.data.navigation.activeRunRoute
import com.arrudeia.core.data.navigation.runOverviewRoute
import com.arrudeia.core.notification.ActiveRunService
import com.arrudeia.feature.trail.presentation.ui.active_run.ActiveRunScreenRoot
import com.arrudeia.feature.trail.presentation.ui.run_overview.RunOverviewScreenRoot

fun NavGraphBuilder.runOverviewScreen(
    onActiveRunClick: (String) -> Unit,
    showBottomBar: (Boolean) -> Unit,
) {
    composable(route = runOverviewRoute) {
        showBottomBar(true)
        RunOverviewScreenRoot(
            onActiveRunClick = onActiveRunClick
        )
    }
}

fun NavGraphBuilder.activeRunScreen(
    onBackClick: () -> Unit,
    showBottomBar: (Boolean) -> Unit,
) {
    composable(route = activeRunRoute,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "arrudeia://active_run"
            }
        )) {
        showBottomBar(false)
        val context = LocalContext.current
        ActiveRunScreenRoot(
            onBack = onBackClick,
            onFinish = onBackClick,
            onServiceToggle = { shouldServiceRun ->
                if (shouldServiceRun) {
                    context.startService(
                        ActiveRunService.createStartIntent(
                            context = context,
                            activityClass = MainActivity::class.java
                        )
                    )
                } else {
                    context.startService(
                        ActiveRunService.createStopIntent(context = context)
                    )
                }
            },
        )
    }
}