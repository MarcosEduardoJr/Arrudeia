package com.arrudeia.feature.social.presentation.ui

import android.app.Activity
import android.view.Window
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.feature.social.domain.chat_list.model.MessageRegister
import com.arrudeia.feature.social.domain.chat_list.model.MessageStatus
import com.arrudeia.feature.social.domain.chat_list.model.User
import com.arrudeia.feature.social.presentation.ui.chatAppBar.ChatAppBar
import com.arrudeia.feature.social.presentation.ui.chatAppBar.ProfilePictureDialog
import com.arrudeia.feature.social.presentation.ui.chatInput.ChatInput
import com.arrudeia.feature.social.presentation.ui.chatrow.ReceivedMessageRow
import com.arrudeia.feature.social.presentation.ui.chatrow.SentMessageRow
import com.arrudeia.feature.social.presentation.viewmodel.SocialChatMessagesViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SocialChatMessageScreen(
    chatRoomUUID: String = "",
    opponentUUID: String = "",
    registerUUID: String = "",
    chatViewModel: SocialChatMessagesViewModel = hiltViewModel(),

    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val context = LocalContext.current
    val window: Window? = (context as? Activity)?.window

    window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    val messages = chatViewModel.messages

    chatViewModel.loadMessagesFromFirebase(chatRoomUUID, opponentUUID, registerUUID)


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


    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            ChatAppBar(
                title = "$opponentName $opponentSurname",
                description = opponentStatus.lowercase(),
                pictureUrl = opponentPictureUrl,
                onUserNameClick = {

                },
                onUserProfilePictureClick = {
                    showDialog = true
                },
                onMoreDropDownBlockUserClick = {
                    chatViewModel.blockFriendToFirebase(registerUUID)
                },
                onBackClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
            BottomShadow(height = 4.dp)

            LazyColumn(
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth(),
                state = scrollState
            ) {
                itemsIndexed(messages) { index, message: MessageRegister ->

                    val sdf = remember {
                        SimpleDateFormat("hh:mm", Locale.ROOT)
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
                    if (index == messages.size - 1)
                        Spacer(modifier = Modifier.size(100.dp))
                }
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .imePadding()
                .padding(horizontal = 16.dp)
        ) {
            ChatInput(
                modifier = Modifier.fillMaxWidth(),
                onMessageChange = { messageContent ->
                    chatViewModel.insertMessageToFirebase(
                        chatRoomUUID,
                        messageContent,
                        registerUUID,
                    )
                },
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }


}

@Composable
fun BottomShadow(alpha: Float = 0.1f, height: Dp = 2.dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Gray.copy(alpha = alpha),
                        Color.Transparent,
                    )
                )
            )
    )
}