package com.arrudeia.feature.social.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.component.RectangleItemButtonChat
import com.arrudeia.feature.social.R.string.start_now_to_talk
import com.arrudeia.feature.social.presentation.chat.theme.spacing
import com.arrudeia.feature.social.presentation.navigation.param.MessageParam
import com.arrudeia.feature.social.presentation.viewmodel.SocialMessagesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun SocialMessagesList(
    userListViewModel: SocialMessagesViewModel = hiltViewModel(),
    onChatClick: (MessageParam) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val navController = rememberNavController()

    val toastMessage = userListViewModel.toastMessage.value
    LaunchedEffect(key1 = toastMessage) {
        if (toastMessage != "") {
            onShowSnackbar(toastMessage, "Close")
        }
    }
    var chatRoomUUID: String? by remember { mutableStateOf(null) }
    var opponentUUID: String? by remember { mutableStateOf(null) }
    var oneSignalUserId: String? by remember { mutableStateOf(null) }
    var registerUUID: String? by remember { mutableStateOf(null) }
//    if (chatRoomUUID != null) {
//        LaunchedEffect(key1 = Unit) {
//            navController.popBackStack()
//            navController.navigate(BottomNavItem.Chat.screen_route)
//        }
//    }
    if (chatRoomUUID != null && opponentUUID != null && registerUUID != null && oneSignalUserId != null) {
        LaunchedEffect(key1 = Unit) {
            onChatClick(
                MessageParam(
                    chatRoomUUID.orEmpty(),
                    opponentUUID.orEmpty(),
                    registerUUID.orEmpty()
                )
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        userListViewModel.refreshingFriendList()
    }
    val acceptedFriendRequestList = userListViewModel.acceptedFriendRequestList
    val pendingFriendRequestList = userListViewModel.pendingFriendRequestList

//    var showAlertDialog by remember {
//        mutableStateOf(false)
//    }
//    if (showAlertDialog) {
//        AlertDialogChat(
//            onDismiss = { showAlertDialog = !showAlertDialog },
//            onConfirm = {
//                showAlertDialog = !showAlertDialog
//                userListViewModel.createFriendshipRegisterToFirebase(it)
//            })
//    }

    val scrollState = rememberLazyListState()
    var isRefreshing by remember { userListViewModel.isRefreshing }

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            isRefreshing = true
            userListViewModel.refreshingFriendList()
        },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = MaterialTheme.spacing.large),
                state = state,
                refreshTriggerDistance = trigger,
                fade = true,
                scale = true,
                backgroundColor = colorResource(id = background_grey_F7F7F9),
                contentColor = colorResource(id = colorPrimary)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .focusable()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { keyboardController?.hide() })
                }
        ) {
            Spacer(modifier = Modifier.size(32.dp))
            //  ProfileAppBar()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
//                .weight(1f),
                state = scrollState,
            ) {

                items(acceptedFriendRequestList.value) { item ->

                    RectangleItemButtonChat(
                        img = item.userPictureUrl,
                        name = item.userEmail,
                        description = if (item.lastMessage.message.isEmpty()) stringResource(id = start_now_to_talk) else item.lastMessage.message.toString(),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                onChatClick(
                                    MessageParam(
                                        chatRoomUUID = item.chatRoomUUID,
                                        registerUUID = item.registerUUID,
                                        opponentUUID = item.userUUID,
                                    )
                                )
                            },
                        tag = SimpleDateFormat("hh:mm", Locale.ROOT).format(item.lastMessage.date)
                    )

                    Spacer(modifier = Modifier.size(8.dp))


                }
            }
        }
    }
}