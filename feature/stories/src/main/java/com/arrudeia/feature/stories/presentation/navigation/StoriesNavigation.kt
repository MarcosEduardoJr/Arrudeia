package com.arrudeia.feature.stories.presentation.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.arrudeia.core.data.navigation.storiesRoute
import com.arrudeia.feature.stories.presentation.ui.storiesRoute
import java.net.URLDecoder
import java.net.URLEncoder


private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
const val STORIES_ID_ARG = "storiesIdArg"

internal class StoriesArgs(val storiesId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                URLDecoder.decode(
                    checkNotNull(savedStateHandle[STORIES_ID_ARG]),
                    URL_CHARACTER_ENCODING
                )
            )
}

fun NavController.navigateToStories(storiesId: String) {
    val encodedId = URLEncoder.encode(storiesId, URL_CHARACTER_ENCODING)
    this.navigate("$storiesRoute/$encodedId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.storiesScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "$storiesRoute/{$STORIES_ID_ARG}",
        arguments = listOf(
            navArgument(STORIES_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        storiesRoute(onBackClick = onBackClick)
    }
}




