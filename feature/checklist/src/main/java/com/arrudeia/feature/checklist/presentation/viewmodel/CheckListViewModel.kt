package com.arrudeia.feature.checklist.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.common.R.string.generic_error
import com.arrudeia.core.result.Result
import com.arrudeia.feature.checklist.domain.GetCheckListUseCase
import com.arrudeia.feature.checklist.presentation.map.mapToCheckListUiModel
import com.arrudeia.feature.checklist.presentation.model.CheckListUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: GetCheckListUseCase
) : ViewModel() {



    var uiState: MutableStateFlow<CheckListUiState> =
        MutableStateFlow(CheckListUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CheckListUiState.Loading
    )

    fun fetchData() {
        viewModelScope.launch {
            when (val result = useCase.invoke()) {
                is Result.Success -> {
                    uiState.value = CheckListUiState.Success(
                        item = result.data?.map { it.mapToCheckListUiModel() } ?: listOf()
                    )
                }

                is Result.Error -> {
                    uiState.value = CheckListUiState.Error(generic_error)
                }

                is Result.Loading -> {
                    uiState.value = CheckListUiState.Loading
                }

                is Result.ErrorMessage -> TODO()
            }
        }
    }


    sealed interface CheckListUiState {
        data class Success(val item: List<CheckListUIModel?>) : CheckListUiState
        data class Error(val message: Int) : CheckListUiState
        data object Loading : CheckListUiState
    }

}
