package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.userlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.domain.IsSavedIdDocUserDataStoreUseCase
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
    private val useCaseHasIdentificationDoc: IsSavedIdDocUserDataStoreUseCase,

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

    var hasIdentificationDoc = mutableStateOf(false)
        private set
    var isLoading = mutableStateOf(false)
        private set

    private fun loadingRequest() {
        isLoading.value = !isLoading.value
    }

    fun checkHasIdentificationDoc() {
        loadingRequest()
        viewModelScope.launch {
            val result = useCaseHasIdentificationDoc()
            hasIdentificationDoc.value = result
            loadingRequest()
        }
    }
}