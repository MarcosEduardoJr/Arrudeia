package com.arrudeia.feature.social.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.arrudeia.core.data.navigation.socialRoute
import com.arrudeia.feature.social.presentation.ui.SocialRoute


fun NavGraphBuilder.socialScreen(routeClick: (String) -> Unit) {

    composable(route = socialRoute) {
        SocialRoute(routeClick)
    }
}

