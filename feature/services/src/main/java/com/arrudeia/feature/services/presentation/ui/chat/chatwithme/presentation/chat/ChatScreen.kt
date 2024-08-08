package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.chat

import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.arrudeia.feature.services.presentation.navigation.param.ChatParam
import com.arrudeia.feature.services.presentation.navigation.param.ServiceDetailParam
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.core.SnackbarController
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.chat.chatAppBar.ChatAppBar
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.chat.chatAppBar.ProfilePictureDialog
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.chat.chatInput.ChatInput
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.chat.chatrow.ReceivedMessageRow
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.chat.chatrow.SentMessageRow
import com.example.chatwithme.domain.model.MessageRegister
import com.example.chatwithme.domain.model.MessageStatus
import com.example.chatwithme.domain.model.User
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(
    chatRoomUUID: String = "",
    opponentUUID: String = "",
    registerUUID: String = "",
    chatViewModel: ChatScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    args: ChatParam,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val toastMessage = chatViewModel.toastMessage.value
    LaunchedEffect(key1 = toastMessage) {
        if (toastMessage != "") {
            onShowSnackbar(toastMessage, "Close")
        }
    }
    chatViewModel.loadMessagesFromFirebase(chatRoomUUID, opponentUUID, registerUUID)
    ChatScreenContent(
        chatRoomUUID,
        opponentUUID,
        registerUUID,
        chatViewModel,
        keyboardController!!,
        onBackClick
    )

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreenContent(
    chatRoomUUID: String,
    opponentUUID: String,
    registerUUID: String,
    chatViewModel: ChatScreenViewModel,
    keyboardController: SoftwareKeyboardController,
    onBackClick: () -> Unit
) {
    val messages = chatViewModel.messages

    LaunchedEffect(key1 = Unit) {
        chatViewModel.loadOpponentProfileFromFirebase(opponentUUID)
    }
    var opponentProfileFromFirebase by remember {
        mutableStateOf(User())
    }
    opponentProfileFromFirebase = chatViewModel.opponentProfileFromFirebase.value
    val opponentName = opponentProfileFromFirebase.userName
    val opponentSurname = opponentProfileFromFirebase.userSurName
    val opponentPictureUrl = opponentProfileFromFirebase.userProfilePictureUrl
    val opponentStatus = opponentProfileFromFirebase.status

    var showDialog by remember {
        mutableStateOf(false)
    }
    if (showDialog) {
        ProfilePictureDialog(opponentPictureUrl) {
            showDialog = !showDialog
        }
    }

    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = messages.size)
    val messagesLoadedFirstTime = chatViewModel.messagesLoadedFirstTime.value
    val messageInserted = chatViewModel.messageInserted.value
    var isChatInputFocus by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = messagesLoadedFirstTime, messages, messageInserted) {
        if (messages.isNotEmpty()) {
            scrollState.scrollToItem(
                index = messages.size - 1
            )
        }
    }

    val imePaddingValues = PaddingValues()
    val imeBottomPadding = imePaddingValues.calculateBottomPadding().value.toInt()
    LaunchedEffect(key1 = imeBottomPadding) {
        if (messages.isNotEmpty()) {
            scrollState.scrollToItem(
                index = messages.size - 1
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .focusable()
            .wrapContentHeight()
            .imePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { keyboardController.hide() })
            }
    ) {
        val context = LocalContext.current

        ChatAppBar(
            title = "$opponentName $opponentSurname",
            description = opponentStatus.lowercase(),
            pictureUrl = opponentPictureUrl,
            onUserNameClick = {
                Toast.makeText(context, "User Profile Clicked", Toast.LENGTH_SHORT).show()
            },
            onUserProfilePictureClick = {
                showDialog = true
            },
            onMoreDropDownBlockUserClick = {
                chatViewModel.blockFriendToFirebase(registerUUID)
                //  navController.navigate(BottomNavItem.UserList.fullRoute)
            },
            onBackClick = onBackClick
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = scrollState
        ) {
            items(messages) { message: MessageRegister ->
                val sdf = remember {
                    java.text.SimpleDateFormat("hh:mm", Locale.ROOT)
                }

                when (message.isMessageFromOpponent) {
                    true -> {
                        ReceivedMessageRow(
                            text = message.chatMessage.message,
                            opponentName = opponentName,
                            quotedMessage = null,
                            messageTime = sdf.format(message.chatMessage.date),
                        )
                    }

                    false -> {
                        SentMessageRow(
                            text = message.chatMessage.message,
                            quotedMessage = null,
                            messageTime = sdf.format(message.chatMessage.date),
                            messageStatus = MessageStatus.valueOf(message.chatMessage.status)
                        )
                    }
                }
            }

        }
        ChatInput(
            onMessageChange = { messageContent ->
                chatViewModel.insertMessageToFirebase(
                    chatRoomUUID,
                    messageContent,
                    registerUUID,
                )
            },
            onFocusEvent = {
                isChatInputFocus = it
            }
        )
    }
}