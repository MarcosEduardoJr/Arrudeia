package com.arrudeia.feature.services.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.arrudeia.core.result.Result
import com.arrudeia.core.ui.MetaDataReader
import com.arrudeia.core.ui.VideoItem
import com.arrudeia.feature.services.R.string.erro_message_list_travels
import com.arrudeia.feature.services.domain.GetServiceDetailUseCase
import com.arrudeia.feature.services.presentation.map.toEntity
import com.arrudeia.feature.services.presentation.model.ServiceDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceDetailViewModel @Inject constructor(
    private val useCase: GetServiceDetailUseCase,
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

    var uiState: MutableStateFlow<ServiceDetailUiState> =
        MutableStateFlow(ServiceDetailUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ServiceDetailUiState.Loading
    )

    fun fetchData(id: Int) {
        viewModelScope.launch {
            when (val result = useCase(id)) {
                is Result.Success -> {
                    if (result.data == null) {
                        uiState.value = ServiceDetailUiState.Error(erro_message_list_travels)
                        return@launch
                    }
                    result.data?.let {
                        uiState.value = ServiceDetailUiState.Success(
                            item = it.toEntity()
                        )
                    }
                }

                is Result.Error -> {
                    uiState.value = ServiceDetailUiState.Error(erro_message_list_travels)
                }

                is Result.Loading -> {
                    uiState.value = ServiceDetailUiState.Loading
                }
            }
        }
    }
}


sealed interface ServiceDetailUiState {
    data class Success(val item: ServiceDetailUiModel) : ServiceDetailUiState
    data class Error(val message: Int) : ServiceDetailUiState
    data object Loading : ServiceDetailUiState
}


