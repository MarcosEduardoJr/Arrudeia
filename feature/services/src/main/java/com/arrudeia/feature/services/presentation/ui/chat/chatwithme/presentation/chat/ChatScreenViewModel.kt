package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.model.ChatMessage
import com.example.chatwithme.domain.model.MessageRegister
import com.example.chatwithme.domain.model.User
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.chatScreen.ChatScreenUseCases
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.utils.Response
import com.example.chatwithme.domain.model.MessageStatus
import com.example.chatwithme.domain.model.UserStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val chatScreenUseCases: ChatScreenUseCases
) : ViewModel() {
    var opponentProfileFromFirebase = mutableStateOf(User())
        private set

    var messageInserted = mutableStateOf(false)
        private set

    var messages: List<MessageRegister> by mutableStateOf(listOf())
        private set

    var messagesLoadedFirstTime = mutableStateOf(false)
        private set

    var toastMessage = mutableStateOf("")
        private set

    fun insertMessageToFirebase(
        chatRoomUUID: String,
        messageContent: String,
        registerUUID: String,
    ) {
        viewModelScope.launch {
            chatScreenUseCases.insertMessageToFirebase(
                chatRoomUUID,
                messageContent,
                registerUUID,
            ).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        messageInserted.value = false
                    }

                    is Response.Success -> {
                        messageInserted.value = true
                    }

                    is Response.Error -> {}
                }
            }
        }
    }

    fun loadMessagesFromFirebase(chatRoomUUID: String, opponentUUID: String, registerUUID: String) {
        viewModelScope.launch {
            chatScreenUseCases.loadMessageFromFirebase(chatRoomUUID, opponentUUID, registerUUID)
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {}
                        is Response.Success -> {
                            messages = listOf()
                            for (i in response.data) {
                                if (i.profileUUID == opponentUUID) {
                                    messages =
                                        messages + MessageRegister(i, true) //Opponent Message
                                } else {
                                    messages = messages + MessageRegister(i, false) //User Message
                                }

                            }
                            messagesLoadedFirstTime.value = true
                        }
                        is Response.Error -> {}
                    }
                }
        }
    }
    fun loadOpponentProfileFromFirebase(opponentUUID: String) {
        viewModelScope.launch {
            chatScreenUseCases.opponentProfileFromFirebase(opponentUUID).collect { response ->
                when (response) {
                    is Response.Loading -> {}
                    is Response.Success -> {
                        opponentProfileFromFirebase.value = response.data
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    fun blockFriendToFirebase(registerUUID: String) {
        viewModelScope.launch {
            chatScreenUseCases.blockFriendToFirebase.invoke(registerUUID).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }

                    is Response.Success -> {
                        toastMessage.value = "Friend Blocked"
                    }

                    is Response.Error -> {}
                }
            }
        }
    }
}