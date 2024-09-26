package com.arrudeia.feature.social.presentation.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.domain.GetIsUserLoggedUseCase
import com.arrudeia.core.domain.GetUserImageUseCase
import com.arrudeia.core.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.core.result.Result
import com.arrudeia.feature.social.data.entity.TravelersEntity
import com.arrudeia.feature.social.domain.GetTravelersUseCase
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
    private val getIsUserLoggedUseCase: GetIsUserLoggedUseCase
) : ViewModel() {


    private val _travelers = mutableStateListOf<TravelersEntity>()
    val travelers: List<TravelersEntity> = _travelers

    init {
        if (travelers.isEmpty())
            getTravelers()
    }

    fun addTraveler(traveler: TravelersEntity) {
        _travelers.add(traveler)
    }

    fun addTravelers(travelers: MutableList<TravelersEntity>) {
        _travelers.addAll(travelers)
    }

    fun removeTraveler(traveler: TravelersEntity) {
        _travelers.remove(traveler)
        if (travelers.isEmpty())
            getTravelers()
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

    fun getTravelers() {
        viewModelScope.launch {
            when (val result = getTravelersUseCase()) {
                is Result.Success -> {
                    result.data?.let { data ->
                        travelersUiState.value = TravelersUiState.Success(
                            data
                        )
                    }
                }

                is Result.Error -> {
                    travelersUiState.value = TravelersUiState.Error(
                        null
                    )
                }

                else -> {

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
    data class Success(val data: List<TravelersEntity>) : TravelersUiState
    data class Error(val message: Int?) : TravelersUiState
    data object Loading : TravelersUiState
    data object None : TravelersUiState
}