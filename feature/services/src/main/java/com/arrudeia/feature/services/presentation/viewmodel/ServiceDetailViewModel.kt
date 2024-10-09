package com.arrudeia.feature.services.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.arrudeia.core.common.R.string.generic_error
import com.arrudeia.core.domain.IsSavedIdDocUserDataStoreUseCase
import com.arrudeia.core.result.Result
import com.arrudeia.core.ui.MetaDataReader
import com.arrudeia.core.ui.VideoItem
import com.arrudeia.feature.services.domain.GetServiceDetailUseCase
import com.arrudeia.feature.services.domain.GetUserPersonalInformationUseCase
import com.arrudeia.feature.services.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.feature.services.presentation.map.toEntity
import com.arrudeia.feature.services.presentation.model.ServiceDetailUiModel
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.core.Constants
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.model.FriendListRegister
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.UserListScreenUseCases
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.utils.Response
import com.example.chatwithme.domain.model.FriendStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceDetailViewModel @Inject constructor(
    private val useCase: GetServiceDetailUseCase,
    private val useCaseHasIdentificationDoc: IsSavedIdDocUserDataStoreUseCase,
    private val userUseCase: GetUserPersonalInformationUseCase,
    private val userListScreenUseCases: UserListScreenUseCases,
    private val savedStateHandle: SavedStateHandle,
    val player: Player,
    private val metaDataReader: MetaDataReader
) : ViewModel() {

    private val videoUli = savedStateHandle.getStateFlow("videoUrli", emptyList<String>())

    val videoItems = videoUli.map { url ->
        url.map { url ->
            VideoItem(
                name = "No name",
                mediaItem = MediaItem.fromUri(url),
                contentUrl = url
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        player.prepare()
    }

    fun addVideoUri(uri: String) {
        savedStateHandle["videoUrli"] = videoUli.value + uri
        player.addMediaItem(MediaItem.fromUri(uri))
    }

    fun playVideo() {

        player.play()


    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    var uiState: MutableStateFlow<ServiceDetailUiState> =
        MutableStateFlow(ServiceDetailUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ServiceDetailUiState.Loading
    )

    fun fetchData(id: Int) {
        viewModelScope.launch {
            when (val result = useCase(id)) {
                is Result.Success -> {
                    if (result.data == null) {
                        uiState.value = ServiceDetailUiState.Error(generic_error)
                        return@launch
                    }
                    result.data?.let {
                        uiState.value = ServiceDetailUiState.Success(
                            item = it.toEntity()
                        )
                    }
                }

                is Result.Error -> {
                    uiState.value = ServiceDetailUiState.Error(generic_error)
                }

                is Result.Loading -> {
                    uiState.value = ServiceDetailUiState.Loading
                }

                is Result.ErrorMessage -> TODO()
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


    var toastMessage = mutableStateOf("")
        private set

    var currentUser = mutableStateOf<UserPersonalInformationUseCaseEntity?>(null)
        private set

    fun createFriendshipRegisterToFirebase(uuidUser: String) {
        fetchServiceUser(uuidUser)
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
                is Result.ErrorMessage -> TODO()
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


sealed interface ServiceDetailUiState {
    data class Success(val item: ServiceDetailUiModel) : ServiceDetailUiState
    data class Error(val message: Int) : ServiceDetailUiState
    data object Loading : ServiceDetailUiState
}


