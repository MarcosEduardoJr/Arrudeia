package com.arrudeia.feature.tips.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.core.data.navigation.tipsRoute
import com.arrudeia.feature.aid.presentation.navigation.param.AidDetailParam
import com.arrudeia.feature.receipt.presentation.navigation.param.ReceiptDetailParam
import com.arrudeia.feature.tips.presentation.ui.TipsRoute


fun NavGraphBuilder.tipsScreen(
    routeClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onReceiptDetailClick: (ReceiptDetailParam) -> Unit,
    onBackClick: () -> Unit,
    onAidDetailClick: (AidDetailParam) -> Unit,
) {

    composable(route = tipsRoute) {
        TipsRoute(
            routeClick,
            onShowSnackbar = onShowSnackbar,
            onReceiptDetailClick = onReceiptDetailClick,
            onBackClick = onBackClick,
            onAidDetailClick = onAidDetailClick
        )
    }
}

