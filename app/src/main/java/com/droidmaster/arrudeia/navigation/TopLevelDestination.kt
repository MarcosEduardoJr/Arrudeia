package com.droidmaster.arrudeia.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.arrudeia.core.designsystem.icon.ArrudeiaIcons
import com.droidmaster.arrudeia.R
import com.arrudeia.feature.profile.R.string.profile
import com.arrudeia.feature.home.R.string.title_home

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    HOME(
        selectedIcon = ArrudeiaIcons.Home,
        unselectedIcon = ArrudeiaIcons.Home,
        iconTextId = title_home,
        titleTextId = title_home,
    ),
    PROFILE(
        selectedIcon = ArrudeiaIcons.Person,
        unselectedIcon = ArrudeiaIcons.Person,
        iconTextId = profile,
        titleTextId = profile,
    ),
}
