package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.userlist

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.arrudeia.core.designsystem.R
import com.arrudeia.feature.services.presentation.navigation.param.ChatParam
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.userlist.components.AcceptPendingRequestList
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.userlist.components.PendingFriendRequestList
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.ui.theme.spacing
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Userlist(
    userListViewModel: UserListViewModel = hiltViewModel(),
    onChatClick: (ChatParam) -> Unit,
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
                ChatParam(
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
                backgroundColor = colorResource(id = R.color.background_grey_F7F7F9),
                contentColor = colorResource(id = R.color.colorPrimary)
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
            //  ProfileAppBar()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
//                .weight(1f),
                state = scrollState,
            ) {
                items(acceptedFriendRequestList.value) { item ->
                    AcceptPendingRequestList(onChatClick = {
                        chatRoomUUID = item.chatRoomUUID
                        registerUUID = item.registerUUID
                        opponentUUID = item.userUUID
                        oneSignalUserId = item.oneSignalUserId
                    }, item = item)


                }
                items(pendingFriendRequestList.value) { item ->
                    PendingFriendRequestList(item, {
                        userListViewModel.acceptPendingFriendRequestToFirebase(item.registerUUID)
                        userListViewModel.refreshingFriendList()
                    }, {
                        userListViewModel.cancelPendingFriendRequestToFirebase(item.registerUUID)
                        userListViewModel.refreshingFriendList()
                    })
                }
            }
        }
    }
}