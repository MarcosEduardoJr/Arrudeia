package com.arrudeia.feature.trip.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.common.R.string.generic_error
import com.arrudeia.core.result.Result
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
            when (val result = travelUseCase.invoke(id.toLong())) {
                is Result.Success -> {
                    travelUiState.value = TripDetailUiState.Success(
                        item = result.data.mapTravelToUiModel()
                    )
                }

                is Result.Error -> {
                    travelUiState.value = TripDetailUiState.Error(generic_error)
                }

                is Result.Loading -> {
                    travelUiState.value = TripDetailUiState.Loading
                }

                is Result.ErrorMessage -> TODO()
            }
        }
    }


sealed interface TripDetailUiState {
    data class Success(val item: TripUIModel?) : TripDetailUiState
    data class Error(val message: Int) : TripDetailUiState
    data object Loading : TripDetailUiState
}

}
