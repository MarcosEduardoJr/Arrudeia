package com.arrudeia.feature.social.presentation.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.result.Result
import com.arrudeia.feature.social.domain.InsertMessageToFirebase
import com.arrudeia.feature.social.domain.LoadMessageFromFirebase
import com.arrudeia.feature.social.domain.LoadOpponentProfileFromFirebase
import com.arrudeia.feature.social.domain.chat_list.BlockFriendToFirebase
import com.arrudeia.feature.social.domain.chat_list.model.MessageRegister
import com.arrudeia.feature.social.domain.chat_list.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialChatMessagesViewModel @Inject constructor(
    val blockFriendToFirebase: BlockFriendToFirebase,
    val insertMessageToFirebase: InsertMessageToFirebase,
    val loadMessageFromFirebase: LoadMessageFromFirebase,
    val opponentProfileFromFirebaseUseCase: LoadOpponentProfileFromFirebase
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
             insertMessageToFirebase.invoke(
                chatRoomUUID,
                messageContent,
                registerUUID,
            ).collect { response ->
                when (response) {
                    is Result.Loading -> {
                        messageInserted.value = false
                    }

                    is Result.Success -> {
                        messageInserted.value = true
                    }

                    is Result.Error -> {}
                    is Result.ErrorMessage -> TODO()
                }
            }
        }
    }

    fun loadMessagesFromFirebase(chatRoomUUID: String, opponentUUID: String, registerUUID: String) {
        viewModelScope.launch {
             loadMessageFromFirebase(chatRoomUUID, opponentUUID, registerUUID)
                .collect { response ->
                    when (response) {
                        is Result.Loading -> {}
                        is Result.Success -> {
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
                        is Result.Error -> {}
                        is Result.ErrorMessage -> {}
                    }
                }
        }
    }
    fun loadOpponentProfileFromFirebase(opponentUUID: String) {
        viewModelScope.launch {
            opponentProfileFromFirebaseUseCase(opponentUUID).collect { response ->
                when (response) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        opponentProfileFromFirebase.value = response.data
                    }
                    is Result.Error -> {}
                    is Result.ErrorMessage -> TODO()
                }
            }
        }
    }

    fun blockFriendToFirebase(registerUUID: String) {
        viewModelScope.launch {
             blockFriendToFirebase.invoke(registerUUID).collect { response ->
                when (response) {
                    is Result.Loading -> {
                        toastMessage.value = ""
                    }

                    is Result.Success -> {
                        toastMessage.value = "Friend Blocked"
                    }

                    is Result.Error -> {}
                    is Result.ErrorMessage -> TODO()
                }
            }
        }
    }


}

