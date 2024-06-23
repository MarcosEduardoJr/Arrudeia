package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.userlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.domain.GetUserPersonalInformationUseCase
import com.arrudeia.feature.services.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.core.Constants
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.model.FriendListRegister
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.model.FriendListRow
import com.example.chatwithme.domain.model.FriendStatus
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.UserListScreenUseCases
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListScreenUseCases: UserListScreenUseCases,
    private val userUseCase: GetUserPersonalInformationUseCase
) : ViewModel() {
    var pendingFriendRequestList = mutableStateOf<List<FriendListRegister>>(listOf())
        private set

    var acceptedFriendRequestList = mutableStateOf<List<FriendListRow>>(listOf())
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    var toastMessage = mutableStateOf("")
        private set

    var currentUser = mutableStateOf<UserPersonalInformationUseCaseEntity?>(null)
        private set

    fun refreshingFriendList() {
        viewModelScope.launch {
            isRefreshing.value = true
            loadPendingFriendRequestListFromFirebase()
            loadAcceptFriendRequestListFromFirebase()
             delay(1000)
            isRefreshing.value = false
        }
    }

    fun fetchServiceUser(uuidUser: String) {
        viewModelScope.launch {
            when (val response = userUseCase(uuidUser)) {
                is Result.Loading -> {
                    toastMessage.value = ""
                }

                is Result.Success -> {
                    if (response.data != null) {
                        currentUser.value = response.data
                        checkChatRoomExistFromFirebaseAndCreateIfNot(
                            currentUser.value?.email.orEmpty(),
                            currentUser.value?.uuid.orEmpty(),
                        )
                    }
                }

                is Result.Error -> {}
            }
        }
    }

    fun createFriendshipRegisterToFirebase(uuidUser : String ) {
        fetchServiceUser(uuidUser)
    }

    fun acceptPendingFriendRequestToFirebase(registerUUID: String) {
        viewModelScope.launch {
            userListScreenUseCases.acceptPendingFriendRequestToFirebase.invoke(registerUUID)
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {
                            toastMessage.value = ""
                        }

                        is Response.Success -> {
                            toastMessage.value = "Friend Request Accepted"
                        }

                        is Response.Error -> {}
                    }
                }
        }
    }

    fun cancelPendingFriendRequestToFirebase(registerUUID: String) {
        viewModelScope.launch {
            userListScreenUseCases.rejectPendingFriendRequestToFirebase.invoke(registerUUID)
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {
                            toastMessage.value = ""
                        }

                        is Response.Success -> {
                            toastMessage.value = "Friend Request Canceled"
                        }

                        is Response.Error -> {}
                    }
                }
        }
    }

    private fun loadAcceptFriendRequestListFromFirebase() {
         viewModelScope.launch {
              userListScreenUseCases.loadAcceptedFriendRequestListFromFirebase.invoke()
                  .collect { response ->
                      when (response) {
                          is Response.Loading -> {}
                          is Response.Success -> {
                              if (response.data.isNotEmpty()) {
                                  acceptedFriendRequestList.value = response.data
                              }
                          }

                          is Response.Error -> {}
                      }
                  }
          }
    }

    private fun loadPendingFriendRequestListFromFirebase() {
         viewModelScope.launch {
             userListScreenUseCases.loadPendingFriendRequestListFromFirebase.invoke()
                 .collect { response ->
                     when (response) {
                         is Response.Loading -> {}
                         is Response.Success -> {
                             pendingFriendRequestList.value = response.data
                         }
                         is Response.Error -> {}
                     }
                 }
         }
    }

    private suspend fun checkChatRoomExistFromFirebaseAndCreateIfNot(
        acceptorEmail: String,
        acceptorUUID: String,
    ) {

            userListScreenUseCases.checkChatRoomExistedFromFirebase.invoke(acceptorUUID)
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {}
                        is Response.Success -> {
                            if (response.data == Constants.NO_CHATROOM_IN_FIREBASE_DATABASE) {
                                createChatRoomToFirebase(
                                    acceptorEmail,
                                    acceptorUUID,
                                )
                            } else {
                                checkFriendListRegisterIsExistFromFirebase(
                                    response.data,
                                    acceptorEmail,
                                    acceptorUUID,
                                )
                            }
                        }

                        is Response.Error -> {
                            toastMessage.value = "Tente novamente mais tarde!"
                        }
                    }

        }
    }

    private fun createChatRoomToFirebase(
        acceptorEmail: String,
        acceptorUUID: String,
    ) {
        viewModelScope.launch {
            userListScreenUseCases.createChatRoomToFirebase.invoke(acceptorUUID)
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {}
                        is Response.Success -> {
                            //Chat Room Created.
                            checkFriendListRegisterIsExistFromFirebase(
                                response.data,
                                acceptorEmail,
                                acceptorUUID,
                            )
                        }

                        is Response.Error -> {}
                    }
                }
        }
    }

    private fun checkFriendListRegisterIsExistFromFirebase(
        chatRoomUUID: String,
        acceptorEmail: String,
        acceptorUUID: String,
    ) {
        viewModelScope.launch {
            userListScreenUseCases.checkFriendListRegisterIsExistedFromFirebase.invoke(
                acceptorEmail,
                acceptorUUID
            ).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        toastMessage.value = ""
                    }

                    is Response.Success -> {
                        if (response.data.equals(FriendListRegister())) {
                            toastMessage.value = "Friend Request Sent."
                            createFriendListRegisterToFirebase(
                                chatRoomUUID,
                                acceptorEmail,
                                acceptorUUID,
                            )
                        } else if (response.data.status.equals(FriendStatus.PENDING.toString())) {
                            toastMessage.value = "Already Have Friend Request"
                        } else if (response.data.status.equals(FriendStatus.ACCEPTED.toString())) {
                            toastMessage.value = "You Are Already Friend."
                        } else if (response.data.status.equals(FriendStatus.BLOCKED.toString())) {
                            openBlockedFriendToFirebase(response.data.registerUUID)
                        }
                    }

                    is Response.Error -> {}
                }
            }
        }
    }

    private fun createFriendListRegisterToFirebase(
        chatRoomUUID: String,
        acceptorEmail: String,
        acceptorUUID: String,
    ) {
        viewModelScope.launch {
            userListScreenUseCases.createFriendListRegisterToFirebase.invoke(
                chatRoomUUID,
                acceptorEmail,
                acceptorUUID,
            ).collect { response ->
                when (response) {
                    is Response.Loading -> {}
                    is Response.Success -> {
                    }

                    is Response.Error -> {}
                }

            }
        }
    }

    private fun openBlockedFriendToFirebase(registerUUID: String) {
        viewModelScope.launch {
            userListScreenUseCases.openBlockedFriendToFirebase.invoke(registerUUID)
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {
                            toastMessage.value = ""
                        }

                        is Response.Success -> {
                            if (response.data) {
                                toastMessage.value = "User Block Opened And Accept As Friend"
                            } else {
                                toastMessage.value = "You Are Blocked by User"
                            }

                        }

                        is Response.Error -> {}
                    }
                }
        }
    }
}