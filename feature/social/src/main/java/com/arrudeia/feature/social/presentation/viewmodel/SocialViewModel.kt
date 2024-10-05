package com.arrudeia.feature.social.presentation.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.domain.GetIsUserLoggedUseCase
import com.arrudeia.core.domain.GetUserImageUseCase
import com.arrudeia.core.domain.GetUserUuidUseCase
import com.arrudeia.core.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.core.result.Result
import com.arrudeia.feature.social.data.entity.TravelersEntity
import com.arrudeia.feature.social.domain.GetTravelersUseCase
import com.arrudeia.feature.social.domain.UpdateTravelersUseCase
import com.arrudeia.feature.social.presentation.ui.model.SocialUserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialViewModel @Inject constructor(
    private val userUseCase: GetUserImageUseCase,
    private val getTravelersUseCase: GetTravelersUseCase,
    private val getIsUserLoggedUseCase: GetIsUserLoggedUseCase,
    private val updateTravelersUseCase: UpdateTravelersUseCase,
    private val getUserUuidUseCase: GetUserUuidUseCase,
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
        updateMatchTraveler(traveler,isMatch)
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
            _imgUserUrl.value = userUseCase()

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