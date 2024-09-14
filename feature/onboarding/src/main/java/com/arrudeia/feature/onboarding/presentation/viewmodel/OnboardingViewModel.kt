package com.arrudeia.feature.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.domain.LibKeysUseCase
import com.arrudeia.core.result.Result
import com.arrudeia.feature.onboarding.domain.GetCurrentUserDataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getCurrentUserDataStoreUseCase: GetCurrentUserDataStoreUseCase,
    private val libKeysUseCase: LibKeysUseCase,
) : ViewModel() {

    var currentUserUiState: MutableStateFlow<CurrentUserUiState> =
        MutableStateFlow(CurrentUserUiState.Loading)
    val currentUserSharedFlow = currentUserUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CurrentUserUiState.Loading
    )

    fun getCurrentUser() {
        viewModelScope.launch {
            when (getCurrentUserDataStoreUseCase()) {
                is Result.Success -> {
                    currentUserUiState.value = CurrentUserUiState.Success()
                }

                else -> {
                    currentUserUiState.value = CurrentUserUiState.Error()
                }
            }
        }
    }

    fun loadLibKeys() {
        viewModelScope.launch {
            val result = libKeysUseCase.saveLocalLibKeys()
            getCurrentUser()
        }
    }


    sealed interface CurrentUserUiState {
        data class Success(val success: Boolean = true) : CurrentUserUiState
        data object Loading : CurrentUserUiState

        data class Error(val message: Int = 0) : CurrentUserUiState
    }
}
