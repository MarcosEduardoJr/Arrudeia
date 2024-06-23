package com.arrudeia.feature.services.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.designsystem.component.DropListUiModel
import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.R.string.erro_message_list_travels
import com.arrudeia.feature.services.domain.GetServicesExpertiseUseCase
import com.arrudeia.feature.services.domain.GetServicesUseCase
import com.arrudeia.feature.services.presentation.map.mapToUiModel
import com.arrudeia.feature.services.presentation.map.toEntity
import com.arrudeia.feature.services.presentation.model.ServiceExpertiseUiModel
import com.arrudeia.feature.services.presentation.model.ServiceUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val useCase: GetServicesUseCase,
    private val useCaseExpertise: GetServicesExpertiseUseCase,
) : ViewModel() {

    var uiState: MutableStateFlow<ServiceUiState> =
        MutableStateFlow(ServiceUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ServiceUiState.Loading
    )

    var uiStateExpertise: MutableStateFlow<ServiceExpertiseUiState> =
        MutableStateFlow(ServiceExpertiseUiState.Loading)
    val sharedFlowExpertise = uiStateExpertise.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ServiceExpertiseUiState.Loading
    )

    fun fetchData() {
        viewModelScope.launch {
            when (val result = useCase()) {
                is Result.Success -> {
                    uiState.value = ServiceUiState.Success(
                        list = result.data.toList().map { it.mapToUiModel() }.toList()
                    )
                }

                is Result.Error -> {
                    uiState.value = ServiceUiState.Error(erro_message_list_travels)
                }

                is Result.Loading -> {
                    uiState.value = ServiceUiState.Loading
                }
            }
        }
    }

    fun fetchDataExpertise() {
        viewModelScope.launch {
            when (val result = useCaseExpertise()) {
                is Result.Success -> {
                    uiStateExpertise.value = ServiceExpertiseUiState.Success(
                        list = result.data.toList().map { it.toEntity() }.toList()
                    )
                }

                is Result.Error -> {
                    uiStateExpertise.value =
                        ServiceExpertiseUiState.Error(erro_message_list_travels)
                }

                is Result.Loading -> {
                    uiStateExpertise.value = ServiceExpertiseUiState.Loading
                }
            }
        }
    }
}


sealed interface ServiceUiState {
    data class Success(val list: List<ServiceUIModel>) : ServiceUiState
    data class Error(val message: Int) : ServiceUiState
    data object Loading : ServiceUiState
}

sealed interface ServiceExpertiseUiState {
    data class Success(val list: List<DropListUiModel>) : ServiceExpertiseUiState
    data class Error(val message: Int) : ServiceExpertiseUiState
    data object Loading : ServiceExpertiseUiState
}

