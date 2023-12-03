package com.arrudeia.feature.sign

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.data.repository.UserDataRepository
import com.arrudeia.core.model.data.UserNewsResource
import com.arrudeia.core.ui.NewsFeedUiState
import com.arrudeia.core.ui.NewsFeedUiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    var shouldDisplayUndoBookmark by mutableStateOf(false)
    private var lastRemovedBookmarkId: String? = null

  




    fun clearUndoState() {
        shouldDisplayUndoBookmark = false
        lastRemovedBookmarkId = null
    }
}
