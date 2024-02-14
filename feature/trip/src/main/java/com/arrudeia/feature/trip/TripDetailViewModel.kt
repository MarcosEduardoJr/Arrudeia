package com.arrudeia.feature.trip

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.domain.GetTravelByIdUseCase
import com.arrudeia.core.entity.TravelUseCaseEntity
import com.arrudeia.feature.trip.model.TripUIModel
import com.arrudeia.feature.trip.navigation.TripArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.arrudeia.feature.trip.R.string.erro_message_list_travels

@HiltViewModel
class TripDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val travelUseCase: GetTravelByIdUseCase
) : ViewModel() {

    private val args: TripArgs = TripArgs(savedStateHandle)

    val id = args.id

    private var travelUiState: MutableStateFlow<TripDetailUiState> =
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

    private fun TravelUseCaseEntity?.mapTravelToUiModel(): TripUIModel? {
        var listResult: TripUIModel? = null
        this?.let {
            listResult = TripUIModel(
                id = it.id,
                name = it.name,
                city = it.city,
                state = it.state,
                day = it.day,
                month = it.month,
                year = it.year,
                price = it.price,
                discount = it.discount,
                cover_image_url = it.cover_image_url,
                whatsapp = it.whatsapp,
                description = it.description
            )

        }
        return listResult
    }

    sealed interface TripDetailUiState {
        data class Success(val item: TripUIModel?) : TripDetailUiState
        data class Error(val message: Int) : TripDetailUiState
        data object Loading : TripDetailUiState
    }
}
