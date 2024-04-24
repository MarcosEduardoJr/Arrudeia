package com.arrudeia.feature.stories.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.arrudeia.feature.stories.StoriesRoute
import com.arrudeia.core.data.navigation.storiesRoute
import java.net.URLDecoder
import java.net.URLEncoder


private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
 const val storiesIdArg = "storiesIdArg"

internal class StoriesArgs(val storiesId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                URLDecoder.decode(
                    checkNotNull(savedStateHandle[storiesIdArg]),
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
    onStoriesClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        route = "$storiesRoute/{$storiesIdArg}",
        arguments = listOf(
            navArgument(storiesIdArg) { type = NavType.StringType },
        ),
    ) {
        StoriesRoute(onStoriesId = onStoriesClick, onBackClick = onBackClick)
    }
}


private const val STORIES_GRAPH_ROUTE_PATTERN = "stories_graph"
const val storiesRoute = "stories_route"

fun NavController.navigateToInterestsGraph(navOptions: NavOptions? = null) {
    this.navigate(STORIES_GRAPH_ROUTE_PATTERN, navOptions)
}




