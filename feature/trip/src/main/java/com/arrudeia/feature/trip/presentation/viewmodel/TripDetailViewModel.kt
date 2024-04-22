package com.arrudeia.feature.trip.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.feature.trip.R.string.erro_message_list_travels
import com.arrudeia.feature.trip.domain.GetTravelByIdUseCase
import com.arrudeia.feature.trip.presentation.map.mapTravelToUiModel
import com.arrudeia.feature.trip.presentation.model.TripUIModel
import com.arrudeia.feature.trip.presentation.navigation.TripArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val travelUseCase: GetTravelByIdUseCase
) : ViewModel() {

    private val args: TripArgs = TripArgs(savedStateHandle)

    val id = args.id

    var travelUiState: MutableStateFlow<TripDetailUiState> =
        MutableStateFlow(TripDetailUiState.Loading)
    val travelSharedFlow = travelUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TripDetailUiState.Loading
    )

    fun fetchData() {
        viewModelScope.launch {
            val result = travelUseCase.invoke(id.toLong())
            if (result != null)
                travelUiState.value = TripDetailUiState.Success(
                    item = result.mapTravelToUiModel()
                )
            else {
                travelUiState.value = TripDetailUiState.Error(erro_message_list_travels)
            }
        }
    }


    sealed interface TripDetailUiState {
        data class Success(val item: TripUIModel?) : TripDetailUiState
        data class Error(val message: Int) : TripDetailUiState
        data object Loading : TripDetailUiState
    }
}
