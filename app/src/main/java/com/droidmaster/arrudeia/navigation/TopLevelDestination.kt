package com.droidmaster.arrudeia.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Cookie
import androidx.compose.material.icons.rounded.Hiking
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MedicalServices
import androidx.compose.material.icons.rounded.NoteAlt
import androidx.compose.ui.graphics.vector.ImageVector
import com.arrudeia.core.common.R.string.trail
import com.arrudeia.core.data.navigation.aidRoute
import com.arrudeia.core.data.navigation.checkListRoute
import com.arrudeia.core.data.navigation.homeRoute
import com.arrudeia.core.data.navigation.receiptRoute
import com.arrudeia.core.data.navigation.runOverviewRoute
import com.arrudeia.core.data.navigation.serviceRoute
import com.arrudeia.feature.aid.R.string.aid
import com.arrudeia.feature.checklist.R.string.checklist
import com.arrudeia.feature.home.R.string.title_home
import com.arrudeia.feature.receipt.R.string.receipt
import com.arrudeia.core.common.R.string.services

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
    val route: String,
) {
    HOME(
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Rounded.Home,
        iconTextId = title_home,
        titleTextId = title_home,
        route = homeRoute,
    ),
    SERVICES(
        selectedIcon = Icons.Rounded.Build,
        unselectedIcon = Icons.Rounded.Build,
        iconTextId = services,
        titleTextId = services,
        route = serviceRoute,
    ),
    TRAIL(
        selectedIcon = Icons.Rounded.Hiking,
        unselectedIcon = Icons.Rounded.Hiking,
        iconTextId = trail,
        titleTextId = trail,
        route = runOverviewRoute,
    ),
    RECEIPT(
        selectedIcon = Icons.Rounded.Cookie,
        unselectedIcon = Icons.Rounded.Cookie,
        iconTextId = receipt,
        titleTextId = receipt,
        route = receiptRoute,
    ),
    AID(
        selectedIcon = Icons.Rounded.MedicalServices,
        unselectedIcon = Icons.Rounded.MedicalServices,
        iconTextId = aid,
        titleTextId = aid,
        route = aidRoute,
    ),
    CHECKLIST(
        selectedIcon = Icons.Rounded.NoteAlt,
        unselectedIcon = Icons.Rounded.NoteAlt,
        iconTextId = checklist,
        titleTextId = checklist,
        route = checkListRoute,
    ),

}
