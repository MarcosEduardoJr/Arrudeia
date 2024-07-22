package com.arrudeia.feature.receipt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.result.Result
import com.arrudeia.core.common.R.string.generic_error
import com.arrudeia.feature.receipt.domain.GetReceiptsUseCase
import com.arrudeia.feature.receipt.presentation.map.mapToUiModel
import com.arrudeia.feature.receipt.presentation.model.ReceiptUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val useCase: GetReceiptsUseCase,
) : ViewModel() {

    var uiState: MutableStateFlow<ReceiptsUiState> =
        MutableStateFlow(ReceiptsUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ReceiptsUiState.Loading
    )

    fun fetchData() {
        viewModelScope.launch {
            when (val result = useCase()) {
                is Result.Success -> {
                    uiState.value = ReceiptsUiState.Success(
                        list = result.data?.map { it.mapToUiModel() } ?: listOf()
                    )
                }

                is Result.Error -> {
                    uiState.value = ReceiptsUiState.Error(generic_error)
                }

                is Result.Loading -> {
                    uiState.value = ReceiptsUiState.Loading
                }
            }
        }
    }
}


sealed interface ReceiptsUiState {
    data class Success(val list: List<ReceiptUIModel?>?) : ReceiptsUiState
    data class Error(val message: Int) : ReceiptsUiState
    data object Loading : ReceiptsUiState
}


