package com.arrudeia.feature.trip.presentation.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.arrudeia.feature.trip.presentation.ui.tripDetailRoute
import com.arrudeia.core.data.navigation.tripDetailRoute
import java.net.URLDecoder
import java.net.URLEncoder


private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
const val TRIP_ID_ARG = "tripIdArg"

internal class TripArgs(val id: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                URLDecoder.decode(
                    checkNotNull(savedStateHandle[TRIP_ID_ARG]),
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
        route = "$tripDetailRoute/{$TRIP_ID_ARG}",
        arguments = listOf(
            navArgument(TRIP_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        tripDetailRoute(onBackClick = onBackClick)
    }
}
