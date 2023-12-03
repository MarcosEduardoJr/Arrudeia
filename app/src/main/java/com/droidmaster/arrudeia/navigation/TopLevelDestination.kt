package com.droidmaster.arrudeia.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.droidmaster.arrudeia.R
import com.arrudeia.core.designsystem.icon.ArrudeiaIcons

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int
) {
    HOME(
        selectedIcon = ArrudeiaIcons.Upcoming,
        unselectedIcon = ArrudeiaIcons.UpcomingBorder,
        iconTextId = R.string.app_name,
        titleTextId = R.string.app_name,
    ),
}
