package com.arrudeia.feature.social.presentation.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.core.result.Result
import com.arrudeia.feature.social.domain.chat_list.AcceptPendingFriendRequestToFirebase
import com.arrudeia.feature.social.domain.chat_list.LoadAcceptedFriendRequestListFromFirebase
import com.arrudeia.feature.social.domain.chat_list.LoadPendingFriendRequestListFromFirebase
import com.arrudeia.feature.social.domain.chat_list.RejectPendingFriendRequestToFirebase
import com.arrudeia.feature.social.domain.chat_list.model.FriendListRegister
import com.arrudeia.feature.social.domain.chat_list.model.FriendListRow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialMessagesViewModel @Inject constructor(
    val acceptPendingFriendRequestToFirebase: AcceptPendingFriendRequestToFirebase,
    val loadAcceptedFriendRequestListFromFirebase: LoadAcceptedFriendRequestListFromFirebase,
    val loadPendingFriendRequestListFromFirebase: LoadPendingFriendRequestListFromFirebase,
    val rejectPendingFriendRequestToFirebase: RejectPendingFriendRequestToFirebase,
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


    fun acceptPendingFriendRequestToFirebase(registerUUID: String) {
        viewModelScope.launch {
            acceptPendingFriendRequestToFirebase.invoke(registerUUID)
                .collect { response ->
                    when (response) {
                        is Result.Loading -> {
                            toastMessage.value = ""
                        }

                        is Result.Success -> {
                            toastMessage.value = "Friend Request Accepted"
                        }

                        is Result.Error -> {}
                        is Result.ErrorMessage -> TODO()
                    }
                }
        }
    }

    fun cancelPendingFriendRequestToFirebase(registerUUID: String) {
        viewModelScope.launch {
            rejectPendingFriendRequestToFirebase.invoke(registerUUID)
                .collect { response ->
                    when (response) {
                        is Result.Loading -> {
                            toastMessage.value = ""
                        }

                        is Result.Success -> {
                            toastMessage.value = "Friend Request Canceled"
                        }

                        is Result.Error -> {}
                        is Result.ErrorMessage -> TODO()
                    }
                }
        }
    }

    private fun loadAcceptFriendRequestListFromFirebase() {
        viewModelScope.launch {
            loadAcceptedFriendRequestListFromFirebase.invoke()
                .collect { response ->
                    when (response) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            if (response.data?.isNotEmpty() == true) {
                                acceptedFriendRequestList.value = response.data!!
                            }
                        }

                        is Error -> {}
                        is Result.Error -> TODO()
                        is Result.ErrorMessage -> TODO()
                    }
                }
        }
    }

    private fun loadPendingFriendRequestListFromFirebase() {
        viewModelScope.launch {
            loadPendingFriendRequestListFromFirebase.invoke()
                .collect { response ->
                    when (response) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            pendingFriendRequestList.value = response.data
                        }

                        is Result.Error -> {}
                        is Result.ErrorMessage -> TODO()
                    }
                }
        }
    }


}

