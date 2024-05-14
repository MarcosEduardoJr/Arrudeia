package com.arrudeia.feature.checklist.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.core.data.navigation.checkListRoute
import com.arrudeia.feature.checklist.presentation.ui.CheckListRoute


fun NavGraphBuilder.checkListScreen(
    onBackClick: () -> Unit,
) {
    composable(route = checkListRoute) {
        CheckListRoute(onBackClick = onBackClick)
    }
}
