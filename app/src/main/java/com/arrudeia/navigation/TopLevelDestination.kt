package com.arrudeia.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.TravelExplore
import androidx.compose.ui.graphics.vector.ImageVector
import com.arrudeia.core.common.R.string.explore
import com.arrudeia.core.common.R.string.tips
import com.arrudeia.core.data.navigation.homeRoute
import com.arrudeia.core.data.navigation.tipsRoute

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
    val route: String,
) {
    HOME(
        selectedIcon = Icons.Rounded.TravelExplore,
        unselectedIcon = Icons.Rounded.TravelExplore,
        iconTextId = explore,
        titleTextId = explore,
        route = homeRoute,
    ),
    TIPS(
        selectedIcon = Icons.Rounded.LightMode,
        unselectedIcon = Icons.Rounded.LightMode,
        iconTextId = tips,
        titleTextId = tips,
        route = tipsRoute,
    ),
  /*  TRAIL(
        selectedIcon = Icons.Rounded.Hiking,
        unselectedIcon = Icons.Rounded.Hiking,
        iconTextId = trail,
        titleTextId = trail,
        route = runOverviewRoute,
    ),

    SOCIAL(
        selectedIcon = Icons.Rounded.People,
        unselectedIcon = Icons.Rounded.People,
        iconTextId = social,
        titleTextId = social,
        route = socialRoute,
    ),
*/
}
