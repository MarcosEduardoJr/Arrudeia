package com.arrudeia.feature.home.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.places.domain.GetAllArrudeiaPlacesUseCase
import com.arrudeia.core.places.domain.entity.ArrudeiaPlaceDetailsUseCaseEntity
import com.arrudeia.feature.home.domain.GetAllArrudeiaTvUseCase
import com.arrudeia.feature.home.domain.GetAllTravelHomeUseCase
import com.arrudeia.feature.home.domain.GetUserPersonalInformationUseCase
import com.arrudeia.feature.home.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.feature.home.presentation.map.mapArrTvToUiModel
import com.arrudeia.feature.home.presentation.map.mapTravelsToUiModel
import com.arrudeia.feature.home.presentation.model.ArrudeiaTvUIModel
import com.arrudeia.feature.home.presentation.model.TravelUIModel
import com.arrudeia.feature.home.presentation.model.ProfileUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.arrudeia.core.result.Result
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaAvailablePlaceUiModel
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaPlaceDetailsUiModel
import com.arrudeia.feature.arrudeia.presentation.ui.AvailableOptions
import com.arrudeia.feature.arrudeia.presentation.ui.CategoryOptions
import com.arrudeia.feature.arrudeia.presentation.ui.SubCategoryOptions
import com.arrudeia.feature.home.domain.GetAllStatesByCountryUseCase
import com.arrudeia.feature.home.presentation.map.mapStateToUiModel
import com.arrudeia.feature.home.presentation.model.StateUIModel
import com.google.android.gms.maps.model.LatLng
import com.arrudeia.core.common.R.string.generic_error
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val travelUseCase: GetAllTravelHomeUseCase,
    private val arrTvUseCase: GetAllArrudeiaTvUseCase,
    private val userUseCase: GetUserPersonalInformationUseCase,
    private val statesByCountryUseCase: GetAllStatesByCountryUseCase,
    private val getAllArrudeiaPlacesUseCase: GetAllArrudeiaPlacesUseCase
) : ViewModel() {

    var travelUiState: MutableStateFlow<TravelUiState> =
        MutableStateFlow(TravelUiState.Loading)
    val travelSharedFlow = travelUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ResultUiState.Loading
    )

    var arrTvUiState: MutableStateFlow<ArrudeiaTvUiState> =
        MutableStateFlow(ArrudeiaTvUiState.Loading)
    val arrTvSharedFlow = arrTvUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ResultUiState.Loading
    )

    var stateUiState: MutableStateFlow<StatesUiState> =
        MutableStateFlow(StatesUiState.Loading)
    val stateSharedFlow = stateUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ResultUiState.Loading
    )


    fun fetchDataArrTv() {
        viewModelScope.launch {
            val result = arrTvUseCase()
            if (!result.isNullOrEmpty())
                arrTvUiState.value = ArrudeiaTvUiState.Success(
                    list = result.mapArrTvToUiModel()
                )
            else {
                arrTvUiState.value = ArrudeiaTvUiState.Error(generic_error)
            }
        }
    }

    var states = mutableStateOf(listOf<StateUIModel>())
    var stateChoosed = mutableStateOf<StateUIModel?>(null)

    var country = mutableStateOf<String>("")
    var loading = mutableStateOf(false)
    fun fetchStatesByCountry(countryCode: String) {
        loading.value = true
        viewModelScope.launch {
            val result = statesByCountryUseCase(countryCode)
            if (!result.isNullOrEmpty()) {
                states.value = StatesUiState.Success(
                    list = result.mapStateToUiModel()
                ).list
                fetchPlaces()
            } else {
                stateUiState.value = StatesUiState.Error(generic_error)
            }
            loading.value = false
        }
    }

    val places = mutableStateListOf<ArrudeiaPlaceDetailsUiModel>()
    fun fetchPlaces() {
        viewModelScope.launch {
            when (val result = getAllArrudeiaPlacesUseCase(stateChoosed.value?.name.orEmpty())) {
                is Result.Success -> {
                    places.clear()
                    result.data.toEntity()?.let { places.addAll(it) }
                }
                else -> {}
            }
        }
    }

    private fun List<ArrudeiaPlaceDetailsUseCaseEntity>?.toEntity():
            MutableList<ArrudeiaPlaceDetailsUiModel>? = if (this == null) null
    else {
        val list = mutableListOf<ArrudeiaPlaceDetailsUiModel>()
        this.let { place ->
            place.forEach { item ->
                item.let {
                    val listAvaliable = mutableListOf<ArrudeiaAvailablePlaceUiModel>()
                    item.available?.forEach { itemAvaliable ->
                        listAvaliable.add(
                            ArrudeiaAvailablePlaceUiModel(
                                AvailableOptions.valueOf(itemAvaliable.name)
                            )
                        )
                    }

                    list.add(
                        ArrudeiaPlaceDetailsUiModel(
                            available = listAvaliable,
                            categoryName = CategoryOptions.valueOf(item.categoryName.orEmpty()),
                            description = item.description.orEmpty(),
                            image = item.image.orEmpty(),
                            location = it.location,
                            name = item.name.orEmpty(),
                            phone = item.phone.orEmpty(),
                            priceLevel = item.priceLevel,
                            rating = item.rating,
                            socialNetwork = item.socialNetwork.orEmpty(),
                            subCategoryName = SubCategoryOptions.valueOf(item.subCategoryName.orEmpty()),
                            uuid = item.uuid.orEmpty(), imageBitmap = null,
                            city = item.city.orEmpty(),
                            state = item.state.orEmpty(),
                            country = item.country
                        )
                    )
                }
            }
        }
        list
    }

    fun fetchDataTravels() {
        viewModelScope.launch {
            val result = travelUseCase.invoke()
            if (result.isNotEmpty())
                travelUiState.value = TravelUiState.Success(
                    list = result.mapTravelsToUiModel()
                )
            else
                travelUiState.value = TravelUiState.Error(
                    generic_error
                )
        }
    }

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


