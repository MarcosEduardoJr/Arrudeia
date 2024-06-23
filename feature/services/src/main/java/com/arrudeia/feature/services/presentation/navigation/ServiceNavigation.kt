package com.arrudeia.feature.services.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.arrudeia.core.data.navigation.serviceRoute
import com.arrudeia.core.designsystem.component.DropListUiModel
import com.arrudeia.feature.services.presentation.navigation.param.ChatParam
import com.arrudeia.feature.services.presentation.navigation.param.NewServiceParam
import com.arrudeia.feature.services.presentation.navigation.param.ServiceDetailParam
import com.arrudeia.feature.services.presentation.ui.NewServiceRoute
import com.arrudeia.feature.services.presentation.ui.ServiceDetailRoute
import com.arrudeia.feature.services.presentation.ui.ServiceRoute
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.chat.ChatScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun NavGraphBuilder.servicesScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    serviceDetailNavigationClick: (ServiceDetailParam) -> Unit,
    onChatClick: (ChatParam) -> Unit,
    onNewServiceNavigationClick: (NewServiceParam) -> Unit,
) {

    composable(route = serviceRoute) {
        ServiceRoute(
            onShowSnackbar = onShowSnackbar,
            serviceDetailNavigationClick = serviceDetailNavigationClick,
            onChatClick = onChatClick,
            onNewServiceNavigationClick = onNewServiceNavigationClick
        )
    }
}

fun NavGraphBuilder.newServiceScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<ServiceDetailParam> {
        val args = it.toRoute<ServiceDetailParam>()
        ServiceDetailRoute(
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar,
            args = args
        )
    }
    composable<ChatParam> {
        val args = it.toRoute<ChatParam>()
        ChatScreen(
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar,
            args = args,
            chatRoomUUID = args.chatRoomUUID,
            opponentUUID = args.opponentUUID,
            registerUUID = args.registerUUID,
        )
    }

    composable<NewServiceParam> {
        val args = it.toRoute<NewServiceParam>()

        NewServiceRoute(
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}

internal inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)