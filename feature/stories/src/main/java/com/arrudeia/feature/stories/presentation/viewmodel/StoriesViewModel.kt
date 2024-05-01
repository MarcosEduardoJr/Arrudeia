package com.arrudeia.feature.stories.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.result.Result
import com.arrudeia.feature.stories.domain.GetAllStoriesByIdUseCase
import com.arrudeia.feature.stories.presentation.model.StoriesUIModel
import com.arrudeia.feature.stories.presentation.navigation.StoriesArgs
import com.arrudeia.feature.stories.presentation.navigation.map.mapStoriesToUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: GetAllStoriesByIdUseCase,
) : ViewModel() {

    private val args: StoriesArgs = StoriesArgs(savedStateHandle)

    val storiesId = args.storiesId

    var uiState: MutableStateFlow<StoriesUiState> =
        MutableStateFlow(StoriesUiState.Loading)
    val storiesSharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = StoriesUiState.Loading
    )

    fun fetchStories() {
        viewModelScope.launch {

            when (val result = useCase.invoke(storiesId.toLong())) {
                is Result.Success -> {
                    uiState.value = StoriesUiState.Success(
                        result.data.mapStoriesToUiModel()
                    )
                }

                is Result.Error -> {
                    uiState.value = StoriesUiState.Error(result.message)
                }

                else -> {
                    uiState.value = StoriesUiState.Loading
                }
            }
        }
    }


    sealed interface StoriesUiState {
        data class Success(val list: List<StoriesUIModel>) : StoriesUiState
        data class Error(val message: Int?) : StoriesUiState
        data object Loading : StoriesUiState
    }


}
