package com.arrudeia.feature.home.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.result.Result
import com.arrudeia.feature.home.data.entity.events.GoogleEventResponse
import com.arrudeia.feature.home.data.entity.hotel.HotelDetailResponse
import com.arrudeia.feature.home.data.entity.hotel.HotelSearchResponse
import com.arrudeia.feature.home.domain.FetchHotelDetailUseCase
import com.arrudeia.feature.home.domain.GetUserPersonalInformationUseCase
import com.arrudeia.feature.home.domain.SearchGoogleEventUseCase
import com.arrudeia.feature.home.domain.SearchHotelsUseCase
import com.arrudeia.feature.home.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.feature.home.presentation.model.ArrudeiaTvUIModel
import com.arrudeia.feature.home.presentation.model.ProfileUiModel
import com.arrudeia.feature.home.presentation.model.StateUIModel
import com.arrudeia.feature.home.presentation.model.TravelUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: GetUserPersonalInformationUseCase,
    private val searchHotelsUseCase: SearchHotelsUseCase,
    private val fetchHotelDetailUseCase: FetchHotelDetailUseCase,
    private val searchGoogleEventUseCase: SearchGoogleEventUseCase
) : ViewModel() {





    private val _hotelSearchState = MutableStateFlow<HotelSearchState>(HotelSearchState.Loading)
    val hotelSearchState: StateFlow<HotelSearchState> = _hotelSearchState.asStateFlow()
    fun searchHotels(
        query: String,
        checkInDate: String,
        checkOutDate: String,
        adults: Int,
        children: Int,
        nextPageToken: String,
        childrenAges: String,
    ) {
        viewModelScope.launch {
            try {
                val response = searchHotelsUseCase(
                    query,
                    checkInDate,
                    checkOutDate,
                    adults,
                    children,
                    nextPageToken,
                    childrenAges
                )
                _hotelSearchState.value = HotelSearchState.Success(response)
            } catch (e: Exception) {
                _hotelSearchState.value = HotelSearchState.Error(e.message ?: "Unknown error")
            }
        }
    }


    private val _eventSearchState =
        MutableStateFlow<GoogleEventSearchState>(GoogleEventSearchState.Loading)
    val eventSearchState: StateFlow<GoogleEventSearchState> = _eventSearchState.asStateFlow()
    fun searchEvents(query: String) {
        viewModelScope.launch {
            try {
                val response = searchGoogleEventUseCase(query)
                _eventSearchState.value = GoogleEventSearchState.Success(response)
            } catch (e: Exception) {
                _eventSearchState.value = GoogleEventSearchState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private val _hotelDetailState = MutableStateFlow<HotelDetailState>(HotelDetailState.Loading)
    val hotelDetailState: StateFlow<HotelDetailState> = _hotelDetailState.asStateFlow()
    fun fetchHotelDetail(
        query: String,
        checkInDate: String,
        checkOutDate: String,
        adults: Int,
        children: Int,
        childrenAges: String,
        propertyToken: String,
    ) {
        viewModelScope.launch {
            try {
                val response = fetchHotelDetailUseCase(
                    query,
                    checkInDate,
                    checkOutDate,
                    adults,
                    children,
                    childrenAges,
                    propertyToken
                )
                _hotelDetailState.value = HotelDetailState.Success(response)
            } catch (e: Exception) {
                _hotelDetailState.value = HotelDetailState.Error(e.message ?: "Unknown error")
            }
        }
    }

    sealed class HotelSearchState {
        object Loading : HotelSearchState()
        data class Success(val data: HotelSearchResponse) : HotelSearchState()
        data class Error(val message: String) : HotelSearchState()
    }

    sealed class GoogleEventSearchState {
        object Loading : GoogleEventSearchState()
        data class Success(val data: GoogleEventResponse) : GoogleEventSearchState()
        data class Error(val message: String) : GoogleEventSearchState()
    }

    sealed class HotelDetailState {
        object Loading : HotelDetailState()
        data class Success(val data: HotelDetailResponse) : HotelDetailState()
        data class Error(val message: String) : HotelDetailState()
    }


    var states = mutableStateOf(listOf<StateUIModel>())
    var stateChoosed = mutableStateOf<StateUIModel?>(null)

    var country = mutableStateOf<String>("")
    var loading = mutableStateOf(false)


    var userUiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(ProfileUiState.Loading)
    val userSharedFlow = userUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ProfileUiState.Loading
    )

    fun getUserPersonalInformation() {
        viewModelScope.launch {
            when (val result = userUseCase()) {
                is Result.Success -> {
                    result.data?.let { data ->
                        userUiState.value = ProfileUiState.Success(
                            data.toUiModel()
                        )
                    }
                }

                is Result.Error -> {
                    userUiState.value = ProfileUiState.Error(
                        result.message
                    )
                }

                Result.Loading -> {
                    userUiState.value = ProfileUiState.Loading

                }
            }
        }
    }

    private fun UserPersonalInformationUseCaseEntity.toUiModel(): ProfileUiModel {
        var result: ProfileUiModel
        this.let {
            result = ProfileUiModel(
                name = it.name,
                email = it.email,
                image = it.profileImage
            )
        }
        return result
    }


}


sealed interface ProfileUiState {
    data class Success(val data: ProfileUiModel) : ProfileUiState
    data class Error(val message: Int?) : ProfileUiState
    data object Loading : ProfileUiState
}

sealed interface TravelUiState {
    data class Success(val list: List<TravelUIModel>) : TravelUiState
    data class Error(val message: Int) : TravelUiState
    data object Loading : TravelUiState
}

sealed interface ArrudeiaTvUiState {
    data class Success(val list: List<ArrudeiaTvUIModel>) : ArrudeiaTvUiState
    data class Error(val message: Int) : ArrudeiaTvUiState
    data object Loading : ArrudeiaTvUiState
}

sealed interface StatesUiState {
    data class Success(val list: List<StateUIModel>) : StatesUiState
    data class Error(val message: Int) : StatesUiState
    data object Loading : StatesUiState
}


