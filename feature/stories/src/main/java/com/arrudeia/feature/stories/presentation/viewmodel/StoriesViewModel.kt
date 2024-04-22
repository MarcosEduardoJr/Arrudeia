package com.arrudeia.feature.stories.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.feature.stories.presentation.model.StoriesUIModel
import com.arrudeia.feature.stories.navigation.StoriesArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.arrudeia.feature.stories.R.string.erro_message_list_travels
import com.arrudeia.feature.stories.domain.GetAllStoriesByIdUseCase
import com.arrudeia.feature.stories.navigation.map.mapStoriesToUiModel

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
           val result = useCase.invoke(storiesId.toLong())
            if(result.isNullOrEmpty())
                uiState.value = StoriesUiState.Error(erro_message_list_travels)
            else
                uiState.value = StoriesUiState.Success(
                    result.mapStoriesToUiModel()
                )
        }
    }



    sealed interface StoriesUiState {
        data class Success(val list: List<StoriesUIModel>) : StoriesUiState
        data class Error(val message: Int) : StoriesUiState
        data object Loading : StoriesUiState
    }


}
