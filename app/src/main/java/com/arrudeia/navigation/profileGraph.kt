package com.arrudeia.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.arrudeia.feature.profile.presentation.navigation.profileAddressScreen
import com.arrudeia.feature.profile.presentation.navigation.profileGraph
import com.arrudeia.feature.profile.presentation.navigation.profilePersonalInformationScreen

fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    showBottomBar: (Boolean) -> Unit,
) {
    profileGraph(
        onBackClick = { navController.popBackStack() },
        nestedGraphs = {
            profilePersonalInformationScreen(
                onBackClick = { navController.popBackStack() },
                onShowSnackbar = onShowSnackbar,
                showBottomBar
            )
            profileAddressScreen(
                onBackClick = { navController.popBackStack() },
                onShowSnackbar = onShowSnackbar
            )
        },
        onShowSnackbar = onShowSnackbar,
        showBottomBar = showBottomBar
    )

}
