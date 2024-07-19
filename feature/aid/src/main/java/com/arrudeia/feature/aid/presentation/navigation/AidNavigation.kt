package com.arrudeia.feature.aid.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.arrudeia.core.data.navigation.aidRoute
import com.arrudeia.feature.aid.presentation.navigation.param.AidDetailParam
import com.arrudeia.feature.aid.presentation.ui.AidDetailRoute
import com.arrudeia.feature.aid.presentation.ui.AidRoute

fun NavGraphBuilder.aidScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onReceiptDetailClick: (AidDetailParam) -> Unit,
) {

    composable(route = aidRoute) {
        AidRoute(
            onShowSnackbar = onShowSnackbar,
            onReceiptDetailClick = onReceiptDetailClick
        )
    }
}

fun NavGraphBuilder.aidDetailScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<AidDetailParam> {
        val args = it.toRoute<AidDetailParam>()
        AidDetailRoute(
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar,
            args = args
        )
    }
}