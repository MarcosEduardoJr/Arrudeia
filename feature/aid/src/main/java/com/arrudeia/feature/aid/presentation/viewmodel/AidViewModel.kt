package com.arrudeia.feature.aid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.result.Result
import com.arrudeia.feature.aid.R.string.erro_message_list_travels
import com.arrudeia.feature.aid.domain.GetAidsUseCase
import com.arrudeia.feature.aid.presentation.map.mapToUiModel
import com.arrudeia.feature.aid.presentation.model.AidUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AidViewModel @Inject constructor(
    private val useCase: GetAidsUseCase,
) : ViewModel() {

    var uiState: MutableStateFlow<AidsUiState> =
        MutableStateFlow(AidsUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = AidsUiState.Loading
    )

    fun fetchData() {
        viewModelScope.launch {
            when (val result = useCase()) {
                is Result.Success -> {
                    uiState.value = AidsUiState.Success(
                        list = result.data?.map { it.mapToUiModel() } ?: listOf()
                    )
                }

                is Result.Error -> {
                    uiState.value = AidsUiState.Error(erro_message_list_travels)
                }

                is Result.Loading -> {
                    uiState.value = AidsUiState.Loading
                }
            }
        }
    }
}


sealed interface AidsUiState {
    data class Success(val list: List<AidUIModel?>?) : AidsUiState
    data class Error(val message: Int) : AidsUiState
    data object Loading : AidsUiState
}


