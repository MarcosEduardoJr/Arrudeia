package com.arrudeia.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.domain.GetAllArrudeiaTvUseCase
import com.arrudeia.core.domain.GetAllTravelHomeUseCase
import com.arrudeia.core.entity.ArrudeiaUseCaseEntity
import com.arrudeia.feature.home.R.string.erro_message_list_travels
import com.arrudeia.feature.home.map.mapArrTvToUiModel
import com.arrudeia.feature.home.map.mapTravelsToUiModel
import com.arrudeia.feature.home.model.ArrudeiaTvUIModel
import com.arrudeia.feature.home.model.TravelUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val travelUseCase: GetAllTravelHomeUseCase,
    private val arrTvUseCase: GetAllArrudeiaTvUseCase,
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

    fun fetchDataArrTv() {
        viewModelScope.launch {
            val result = arrTvUseCase.invoke()
            if (!result.isNullOrEmpty())
                arrTvUiState.value = ArrudeiaTvUiState.Success(
                    list = result.mapArrTvToUiModel()
                )
            else {
                arrTvUiState.value = ArrudeiaTvUiState.Error(erro_message_list_travels)
            }
        }
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
                    erro_message_list_travels
                )
        }
    }
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


