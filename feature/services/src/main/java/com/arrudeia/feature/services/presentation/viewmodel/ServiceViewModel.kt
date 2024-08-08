package com.arrudeia.feature.services.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.designsystem.component.DropListUiModel
import com.arrudeia.core.domain.IsSavedIdDocUserDataStoreUseCase
import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.domain.GetServicesExpertiseUseCase
import com.arrudeia.feature.services.domain.GetServicesUseCase
import com.arrudeia.feature.services.presentation.map.mapToUiModel
import com.arrudeia.feature.services.presentation.map.toEntity
import com.arrudeia.feature.services.presentation.model.ServiceUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.arrudeia.core.common.R.string.generic_error

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val useCase: GetServicesUseCase,
    private val useCaseExpertise: GetServicesExpertiseUseCase,
    private val useCaseHasIdentificationDoc: IsSavedIdDocUserDataStoreUseCase,
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
                    uiState.value = ServiceUiState.Error(generic_error)
                }

                is Result.Loading -> {
                    uiState.value = ServiceUiState.Loading
                }
            }
        }
    }

    fun fetchDataExpertise() {
        uiStateExpertise.value = ServiceExpertiseUiState.Loading
        viewModelScope.launch {
            when (val result = useCaseExpertise()) {
                is Result.Success -> {
                    uiStateExpertise.value = ServiceExpertiseUiState.Success(
                        list = result.data.toList().map { it.toEntity() }.toList()
                    )
                }

                is Result.Error -> {
                    uiStateExpertise.value =
                        ServiceExpertiseUiState.Error(generic_error)
                }

                is Result.Loading -> {
                    uiStateExpertise.value = ServiceExpertiseUiState.Loading
                }
            }
        }
    }

    var hasIdentificationDoc = mutableStateOf(false)
        private set
    var isLoading = mutableStateOf(false)
        private set

    private fun loadingRequest() {
        isLoading.value = !isLoading.value
    }

    fun checkHasIdentificationDoc() {
        loadingRequest()
        viewModelScope.launch {
            val result = useCaseHasIdentificationDoc()
            hasIdentificationDoc.value = result
            loadingRequest()
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

