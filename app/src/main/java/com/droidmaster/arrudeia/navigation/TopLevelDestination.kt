package com.droidmaster.arrudeia.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.arrudeia.core.designsystem.icon.ArrudeiaIcons
import com.arrudeia.feature.checklist.R.string.checklist
import com.arrudeia.feature.home.R.string.title_home
import com.arrudeia.feature.profile.R.string.profile
import com.arrudeia.feature.receipt.R.string.receipt

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
    RECEIPT(
        selectedIcon = ArrudeiaIcons.Cookie,
        unselectedIcon = ArrudeiaIcons.Cookie,
        iconTextId = receipt,
        titleTextId = receipt,
    ),
    CHECKLIST(
        selectedIcon = ArrudeiaIcons.NoteAlt,
        unselectedIcon = ArrudeiaIcons.NoteAlt,
        iconTextId = checklist,
        titleTextId = checklist,
    ),
    PROFILE(
        selectedIcon = ArrudeiaIcons.Person,
        unselectedIcon = ArrudeiaIcons.Person,
        iconTextId = profile,
        titleTextId = profile,
    ),

}
