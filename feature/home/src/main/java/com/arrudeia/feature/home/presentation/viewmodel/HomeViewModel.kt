package com.arrudeia.feature.home.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.data.repository.entity.ProfileDataStoreUserRepositoryEntity
import com.arrudeia.core.domain.GetUserPersonalInformationLocalUseCase
import com.arrudeia.feature.home.data.entity.events.GoogleEventResponse
import com.arrudeia.feature.home.data.entity.hotel.HotelDetailResponse
import com.arrudeia.feature.home.data.entity.hotel.HotelSearchResponse
import com.arrudeia.feature.home.domain.FetchHotelDetailUseCase
import com.arrudeia.feature.home.domain.SearchGoogleEventUseCase
import com.arrudeia.feature.home.domain.SearchHotelsUseCase
import com.arrudeia.feature.home.presentation.model.ArrudeiaTvUIModel
import com.arrudeia.feature.home.presentation.model.ProfileUiModel
import com.arrudeia.feature.home.presentation.model.StateUIModel
import com.arrudeia.feature.home.presentation.model.TravelUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: GetUserPersonalInformationLocalUseCase,
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




    private val _user = mutableStateOf<ProfileUiModel?>(null)
    val user: State<ProfileUiModel?> = _user

    fun getUserPersonalInformation() {
        viewModelScope.launch {
            _user.value = userUseCase()?.toUiModel()
        }
    }

    private fun ProfileDataStoreUserRepositoryEntity.toUiModel(): ProfileUiModel {
        var result: ProfileUiModel
        this.let {
            result = ProfileUiModel(
                name = it.name,
                email = it.email,
                image = it.image
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


