package com.arrudeia.feature.receipt.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.arrudeia.core.data.navigation.receiptRoute
import com.arrudeia.feature.receipt.presentation.navigation.param.ReceiptDetailParam
import com.arrudeia.feature.receipt.presentation.ui.ReceiptDetailRoute
import com.arrudeia.feature.receipt.presentation.ui.ReceiptRoute

fun NavGraphBuilder.receiptScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onReceiptDetailClick: (ReceiptDetailParam) -> Unit,
    onBackClick: () -> Unit,
) {

    composable(route = receiptRoute) {
        ReceiptRoute(
            onShowSnackbar = onShowSnackbar,
            onReceiptDetailClick = onReceiptDetailClick,
            onBackClick
        )
    }
}

fun NavGraphBuilder.receiptDetailScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<ReceiptDetailParam> {
        val args = it.toRoute<ReceiptDetailParam>()
        ReceiptDetailRoute(
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar,
            args = args
        )
    }
}