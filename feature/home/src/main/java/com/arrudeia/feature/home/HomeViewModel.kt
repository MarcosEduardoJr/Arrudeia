package com.arrudeia.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.data.interactions.Result.Success
import com.arrudeia.core.domain.GetAllArrudeiaTvUseCase
import com.arrudeia.core.domain.GetAllTravelHomeUseCase
import com.arrudeia.core.entity.ArrudeiaUseCaseEntity
import com.arrudeia.core.entity.TravelUseCaseEntity
import com.arrudeia.feature.home.R.string.erro_message_list_travels
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

    private var travelUiState: MutableStateFlow<TravelUiState> =
        MutableStateFlow(TravelUiState.Loading)
    val travelSharedFlow = travelUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ResultUiState.Loading
    )

    private var arrTvUiState: MutableStateFlow<ArrudeiaTvUiState> =
        MutableStateFlow(ArrudeiaTvUiState.Loading)
    val arrTvSharedFlow = arrTvUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ResultUiState.Loading
    )

    fun fetchDataArrTv() {
        viewModelScope.launch {
            when (val result = arrTvUseCase.invoke()) {
                is Success -> {
                    arrTvUiState.value = ArrudeiaTvUiState.Success(
                        list = result.result.mapArrTvToUiModel()
                    )
                }

                else -> {
                    arrTvUiState.value = ArrudeiaTvUiState.Error(erro_message_list_travels)
                }
            }
        }
    }

    fun fetchDataTravels() {
        viewModelScope.launch {
            when (val result = travelUseCase.invoke()) {
                is Success -> {
                    travelUiState.value = TravelUiState.Success(
                        list = result.result.mapTravelsToUiModel()
                    )
                }

                else -> {
                    travelUiState.value = TravelUiState.Error(erro_message_list_travels)
                }
            }
        }
    }


    private fun List<TravelUseCaseEntity>?.mapTravelsToUiModel(): List<TravelUIModel> {
        val listResult = mutableListOf<TravelUIModel>()
        this?.forEach {
            listResult.add(
                TravelUIModel(
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
                    whatsapp = it.whatsapp
                )
            )
        }
        return listResult
    }

    private fun List<ArrudeiaUseCaseEntity>?.mapArrTvToUiModel(): List<ArrudeiaTvUIModel> {
        val listResult = mutableListOf<ArrudeiaTvUIModel>()
        this?.forEach {
            listResult.add(
                ArrudeiaTvUIModel(
                    imageUrl = it.imageUrl
                )
            )
        }
        return listResult
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


