package com.arrudeia.feature.social.presentation.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.domain.GetIsUserLoggedUseCase
import com.arrudeia.core.domain.GetUserImageUseCase
import com.arrudeia.core.domain.GetUserPersonalInformationLocalUseCase
import com.arrudeia.core.domain.GetUserUuidUseCase
import com.arrudeia.core.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.core.result.Result
import com.arrudeia.core.utils.Constants
import com.arrudeia.feature.social.data.entity.TravelersEntity
import com.arrudeia.feature.social.domain.GetTravelersUseCase
import com.arrudeia.feature.social.domain.UpdateTravelersUseCase
import com.arrudeia.feature.social.domain.chat_list.AcceptPendingFriendRequestToFirebase
import com.arrudeia.feature.social.domain.chat_list.CheckChatRoomExistedFromFirebase
import com.arrudeia.feature.social.domain.chat_list.CheckFriendListRegisterIsExistedFromFirebase
import com.arrudeia.feature.social.domain.chat_list.CreateChatRoomToFirebase
import com.arrudeia.feature.social.domain.chat_list.CreateFriendListRegisterToFirebase
import com.arrudeia.feature.social.domain.chat_list.LoadAcceptedFriendRequestListFromFirebase
import com.arrudeia.feature.social.domain.chat_list.LoadPendingFriendRequestListFromFirebase
import com.arrudeia.feature.social.domain.chat_list.OpenBlockedFriendToFirebase
import com.arrudeia.feature.social.domain.chat_list.RejectPendingFriendRequestToFirebase
import com.arrudeia.feature.social.domain.chat_list.SearchUserFromFirebase
import com.arrudeia.feature.social.domain.chat_list.model.FriendStatus
import com.arrudeia.feature.social.presentation.ui.model.SocialUserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialTravelersViewModel @Inject constructor(
    private val userImageUseCase: GetUserImageUseCase,
    private val userUseCase: GetUserPersonalInformationLocalUseCase,
    private val getTravelersUseCase: GetTravelersUseCase,
    private val getIsUserLoggedUseCase: GetIsUserLoggedUseCase,
    private val updateTravelersUseCase: UpdateTravelersUseCase,
    private val getUserUuidUseCase: GetUserUuidUseCase,
    private val acceptPendingFriendRequestToFirebase: AcceptPendingFriendRequestToFirebase,
    private val checkChatRoomExistedFromFirebase: CheckChatRoomExistedFromFirebase,
    private val checkFriendListRegisterIsExistedFromFirebase: CheckFriendListRegisterIsExistedFromFirebase,
    private val createChatRoomToFirebase: CreateChatRoomToFirebase,
    private val createFriendListRegisterToFirebase: CreateFriendListRegisterToFirebase,
    private val loadAcceptedFriendRequestListFromFirebase: LoadAcceptedFriendRequestListFromFirebase,
    private val loadPendingFriendRequestListFromFirebase: LoadPendingFriendRequestListFromFirebase,
    private val openBlockedFriendToFirebase: OpenBlockedFriendToFirebase,
    private val rejectPendingFriendRequestToFirebase: RejectPendingFriendRequestToFirebase,
    private val searchUserFromFirebase: SearchUserFromFirebase,
) : ViewModel() {

    private val _travelers = mutableStateListOf<TravelersEntity>()
    val travelers: List<TravelersEntity> = _travelers

    private var currentUserUUID: String? = null

    private val MATCH = 1
    private val UN_MATCH = -1

    init {
        if (currentUserUUID.isNullOrEmpty())
            getUserUuid()

        if (travelers.isEmpty())
            getTravelers()
    }

    private fun getUserUuid() {
        viewModelScope.launch {
            getUserUuidUseCase().let {
                currentUserUUID = it
            }
        }
    }

    fun removeTraveler(traveler: TravelersEntity, isMatch: Boolean = false) {
        updateMatchTraveler(traveler, isMatch)
        _travelers.remove(traveler)
        if (travelers.isEmpty())
            getTravelers()
    }

    private var updateTravelersUiState: MutableStateFlow<UpdateTravelerUiState> =
        MutableStateFlow(UpdateTravelerUiState.None)
    val updateTravelersSharedFlow = updateTravelersUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UpdateTravelerUiState.None
    )

    private fun updateMatchTraveler(traveler: TravelersEntity, isMatch: Boolean = false) {

        val sendMatch = calculateMatch(
            traveler.travelerSendId,
            isMatch,
            currentUserUUID,
            traveler.travelerSendMatch
        )
        val receiveMatch = calculateMatch(
            traveler.travelerReceiveId,
            isMatch,
            currentUserUUID,
            traveler.travelerReceiveMatch
        )

        viewModelScope.launch {
            updateTravelersUiState.value = UpdateTravelerUiState.Loading
            when (val result = updateTravelersUseCase(
                travelerReceive = traveler.travelerReceiveId,
                travelerSend = traveler.travelerSendId,
                travelerSendMatch = sendMatch,
                travelerReceiveMatch = receiveMatch
            )) {
                is Result.Loading -> {
                    updateTravelersUiState.value = UpdateTravelerUiState.Loading
                }

                is Result.Success -> {
                    if (sendMatch == MATCH && receiveMatch == MATCH) {
                        createChatRoom(
                            acceptorUUID = traveler.travelerSendId,
                            acceptorEmailUUID = traveler.travelerSendEmail,
                            traveler.name
                        )
                    }
                }

                else -> {
                    updateTravelersUiState.value = UpdateTravelerUiState.Saved
                    updateTravelersUiState.value = UpdateTravelerUiState.None
                }
            }
        }
    }

    private fun calculateMatch(
        travelerId: String,
        isMatch: Boolean,
        currentUserUUID: String?,
        currentMatch: Int
    ): Int {
        return if (travelerId.contentEquals(currentUserUUID)) {
            if (isMatch) MATCH else UN_MATCH
        } else {
            currentMatch
        }
    }

    private val _imgUserUrl = mutableStateOf("")
    val imgUserUrl: State<String> = _imgUserUrl


    fun getUserPersonalInformation() {
        viewModelScope.launch {
            _imgUserUrl.value = userImageUseCase()

        }
    }

    private val _isUserLoggedUseCase = mutableStateOf(true)
    val isUserLoggedUseCase: State<Boolean> = _isUserLoggedUseCase

    fun isUserLogged() {
        viewModelScope.launch {
            _isUserLoggedUseCase.value = getIsUserLoggedUseCase()
        }
    }

    var travelersUiState: MutableStateFlow<TravelersUiState> =
        MutableStateFlow(TravelersUiState.Loading)
    val travelersSharedFlow = travelersUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TravelersUiState.Loading
    )

    var currentTravelersPage = 0

    // SocialViewModel.kt
    fun getTravelers() {
        viewModelScope.launch {
            when (val result = getTravelersUseCase(++currentTravelersPage)) {
                is Result.Success -> {
                    result.data?.let { data ->
                        if (data.isNotEmpty()) {
                            _travelers.addAll(result.data.orEmpty())
                            travelersUiState.value = TravelersUiState.None
                        } else {
                            travelersUiState.value = TravelersUiState.NoMoreTravelers
                        }
                    }
                }

                is Result.Error -> {
                    travelersUiState.value = TravelersUiState.NoMoreTravelers
                }

                else -> {
                    travelersUiState.value = TravelersUiState.NoMoreTravelers
                }
            }
        }
    }

    private fun UserPersonalInformationUseCaseEntity.toUiModel(): SocialUserUiModel {
        var result: SocialUserUiModel
        this.let {
            result = SocialUserUiModel(
                name = it.name,
                email = it.email,
                image = it.profileImage
            )
        }
        return result
    }

    var toastMessage = mutableStateOf("")
        private set

    var currentUser = mutableStateOf<UserPersonalInformationUseCaseEntity?>(null)
        private set

    fun createChatRoom(
        acceptorUUID: String,
        acceptorEmailUUID: String,
        acceptorName: String,
    ) {
        viewModelScope.launch {
            val response = userUseCase()
            if (response != null) {
                currentUser.value = UserPersonalInformationUseCaseEntity(
                    uuid = response.uid,
                    name = response.name,
                    email = response.email,
                    profileImage = response.image
                )
                checkChatRoomExistFromFirebaseAndCreateIfNot(
                    acceptorUUID,
                    acceptorEmailUUID = acceptorEmailUUID,
                    acceptorName = acceptorName,
                    requesterName = response.name
                )
            }
        }
    }


    private suspend fun checkChatRoomExistFromFirebaseAndCreateIfNot(
        acceptorUUID: String,
        acceptorEmailUUID: String,
        acceptorName: String,
        requesterName: String
    ) {

        checkChatRoomExistedFromFirebase(acceptorUUID)
            .collect { response ->
                when (response) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        if (response.data == Constants.NO_CHATROOM_IN_FIREBASE_DATABASE) {
                            createNewChatRoomToFirebase(
                                acceptorUUID = acceptorUUID, acceptorEmail = acceptorEmailUUID, acceptorName = acceptorName, requesterName = requesterName
                            )
                        } else {
                            checkFriendListRegisterIsExistFromFirebase(
                                response.data,
                                acceptorUUID = acceptorUUID,
                                acceptorEmailUUID = acceptorEmailUUID,
                                acceptorName = acceptorName,
                                requesterName =  requesterName
                            )
                        }
                    }

                    is Result.Error -> {
                        toastMessage.value = "Tente novamente mais tarde!"
                    }

                    is Result.ErrorMessage -> TODO()
                }

            }
    }

    private fun checkFriendListRegisterIsExistFromFirebase(
        chatRoomUUID: String,
        acceptorUUID: String,
        acceptorEmailUUID: String,
        acceptorName : String,
        requesterName : String
    ) {
        viewModelScope.launch {
            checkFriendListRegisterIsExistedFromFirebase(
                acceptorUUID, acceptorEmailUUID
            ).collect { response ->
                when (response) {
                    is Result.Loading -> {
                        toastMessage.value = ""
                    }

                    is Result.Success -> {
                        if (response.data == null) {
                            toastMessage.value = "Friend Request Sent."
                            createChatEmptyChatRoom(chatRoomUUID, acceptorEmailUUID, acceptorUUID, acceptorName = acceptorName, requesterName = requesterName)

                        } else if (response.data?.status.equals(FriendStatus.PENDING.toString())) {
                            toastMessage.value = "Already Have Friend Request"
                        } else if (response.data?.status.equals(FriendStatus.ACCEPTED.toString())) {
                            toastMessage.value = "You Are Already Friend."
                        } else if (response.data?.status.equals(FriendStatus.BLOCKED.toString())) {
                            openBlockedFriendToFirebase(response.data?.registerUUID.orEmpty())
                        }
                    }

                    is Result.Error -> {}
                    is Result.ErrorMessage -> TODO()
                }
            }
        }
    }

    private suspend fun createChatEmptyChatRoom(
        chatRoomUUID: String,
        acceptorEmailUUID: String,
        acceptorUUID: String,
        acceptorName : String,
        requesterName : String
    ) {
        createFriendListRegisterToFirebase.invoke(
            chatRoomUUID = chatRoomUUID,
            acceptorEmail = acceptorEmailUUID,
            acceptorUUID = acceptorUUID,
            acceptorName = acceptorName,
            requesterName = requesterName
        ).collect { result ->
            updateTravelersUiState.value = UpdateTravelerUiState.Saved
            updateTravelersUiState.value = UpdateTravelerUiState.None
        }
    }

    private fun createNewChatRoomToFirebase(
        acceptorEmail: String,
        acceptorUUID: String,
        acceptorName : String,
        requesterName : String
    ) {
        viewModelScope.launch {
            createChatRoomToFirebase.invoke(acceptorUUID)
                .collect { response ->
                    when (response) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            //Chat Room Created.
                            checkFriendListRegisterIsExistFromFirebase(
                                response.data,
                                acceptorUUID = acceptorUUID,
                                acceptorEmailUUID = acceptorEmail,
                                acceptorName = acceptorName,
                                requesterName = requesterName
                            )
                        }

                        is Result.Error -> {}
                        is Result.ErrorMessage -> TODO()
                    }
                }
        }
    }


}

sealed interface TravelersUiState {
    data object Loading : TravelersUiState
    data object None : TravelersUiState
    data object NoMoreTravelers : TravelersUiState
}

sealed interface UpdateTravelerUiState {
    data object Loading : UpdateTravelerUiState
    data object None : UpdateTravelerUiState
    data object Saved : UpdateTravelerUiState
}