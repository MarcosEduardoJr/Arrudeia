package com.arrudeia.feature.trip.presentation.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.arrudeia.feature.trip.presentation.ui.TripDetailRoute
import com.arrudeia.navigation.tripDetailRoute
import java.net.URLDecoder
import java.net.URLEncoder


private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
const val tripIdArg = "tripIdArg"

internal class TripArgs(val id: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                URLDecoder.decode(
                    checkNotNull(savedStateHandle[tripIdArg]),
                    URL_CHARACTER_ENCODING
                )
            )
}

fun NavController.navigateToTripDetail(id: String) {
    val encodedId = URLEncoder.encode(id, URL_CHARACTER_ENCODING)
    this.navigate("$tripDetailRoute/$encodedId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.tripDetailScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = "$tripDetailRoute/{$tripIdArg}",
        arguments = listOf(
            navArgument(tripIdArg) { type = NavType.StringType },
        ),
    ) {
        TripDetailRoute(onBackClick = onBackClick)
    }
}
