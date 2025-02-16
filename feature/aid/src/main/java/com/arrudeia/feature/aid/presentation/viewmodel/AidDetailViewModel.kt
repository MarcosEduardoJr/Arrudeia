package com.arrudeia.feature.aid.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.arrudeia.core.common.R.string.generic_error
import com.arrudeia.core.result.Result
import com.arrudeia.core.ui.MetaDataReader
import com.arrudeia.core.ui.VideoItem
import com.arrudeia.feature.aid.domain.GetAidDetailUseCase
import com.arrudeia.feature.aid.presentation.map.toEntity
import com.arrudeia.feature.aid.presentation.model.AidDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AidDetailViewModel @Inject constructor(
    private val useCase: GetAidDetailUseCase,
    private val savedStateHandle: SavedStateHandle,
    val player: Player,
    private val metaDataReader: MetaDataReader
) : ViewModel() {

    private val videoUli = savedStateHandle.getStateFlow("videoUrli", emptyList<String>())

    val videoItems = videoUli.map { url ->
        url.map { url ->
            VideoItem(
                name = "No name",
                mediaItem = MediaItem.fromUri(url),
                contentUrl = url
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        player.prepare()
    }

    fun addVideoUri(uri: String) {
        savedStateHandle["videoUrli"] = videoUli.value + uri
        player.addMediaItem(MediaItem.fromUri(uri))
    }

    fun playVideo() {

        player.play()


    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    var uiState: MutableStateFlow<AidDetailUiState> =
        MutableStateFlow(AidDetailUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = AidDetailUiState.Loading
    )

    fun fetchData(id: String) {
        viewModelScope.launch {
            when (val result = useCase(id)) {
                is Result.Success -> {
                    if (result.data == null) {
                        uiState.value = AidDetailUiState.Error(generic_error)
                        return@launch
                    }
                    result.data?.let {
                        uiState.value = AidDetailUiState.Success(
                            item = it.toEntity()
                        )
                    }
                }

                is Result.Error -> {
                    uiState.value = AidDetailUiState.Error(generic_error)
                }

                is Result.Loading -> {
                    uiState.value = AidDetailUiState.Loading
                }

                is Result.ErrorMessage -> TODO()
            }
        }
    }
}


sealed interface AidDetailUiState {
    data class Success(val item: AidDetailUiModel) : AidDetailUiState
    data class Error(val message: Int) : AidDetailUiState
    data object Loading : AidDetailUiState
}


