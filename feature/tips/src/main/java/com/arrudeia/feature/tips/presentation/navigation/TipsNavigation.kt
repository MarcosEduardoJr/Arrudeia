package com.arrudeia.feature.tips.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.core.data.navigation.tipsRoute
import com.arrudeia.feature.tips.presentation.ui.TipsRoute


fun NavGraphBuilder.tipsScreen(routeClick: (String) -> Unit) {

    composable(route = tipsRoute) {
        TipsRoute(routeClick)
    }
}

