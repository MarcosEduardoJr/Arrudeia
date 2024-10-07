package com.arrudeia.feature.social.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.arrudeia.core.data.navigation.socialRoute
import com.arrudeia.feature.social.presentation.navigation.param.MessageParam
import com.arrudeia.feature.social.presentation.ui.SocialChatMessageScreen
import com.arrudeia.feature.social.presentation.ui.SocialRoute


fun NavGraphBuilder.socialScreen(
    routeClick: (String) -> Unit,
    onMessageClick: (MessageParam) -> Unit
) {

    composable(route = socialRoute) {
        SocialRoute(routeClick, onMessageClick = onMessageClick)
    }
}


fun NavGraphBuilder.messageScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<MessageParam> {
        val args = it.toRoute<MessageParam>()
        SocialChatMessageScreen(
            chatRoomUUID = args.chatRoomUUID,
            opponentUUID = args.opponentUUID,
            registerUUID = args.registerUUID,
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}

