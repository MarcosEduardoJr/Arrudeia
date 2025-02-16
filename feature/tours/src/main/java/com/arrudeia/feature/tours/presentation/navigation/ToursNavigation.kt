package com.arrudeia.feature.tours.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.arrudeia.feature.tours.presentation.navigation.param.ToursStoreParam
import com.arrudeia.feature.tours.presentation.navigation.param.ToursStoreProductParam
import com.arrudeia.feature.tours.presentation.ui.ToursStoreProductScreen
import com.arrudeia.feature.tours.presentation.ui.ToursStoreScreen

fun NavGraphBuilder.toursStoreScreen(
    onBackClick: () -> Unit,
    showBottomBar: (Boolean) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<ToursStoreParam> {
        val args = it.toRoute<ToursStoreParam>()
        showBottomBar(false)
        ToursStoreScreen(onBackClick,args,onShowSnackbar = onShowSnackbar)
    }
    composable<ToursStoreProductParam> {
        val args = it.toRoute<ToursStoreProductParam>()
        showBottomBar(false)
        ToursStoreProductScreen(onBackClick,args,onShowSnackbar = onShowSnackbar)
    }
}