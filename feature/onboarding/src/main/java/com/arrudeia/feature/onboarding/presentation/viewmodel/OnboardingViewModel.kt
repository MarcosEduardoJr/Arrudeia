package com.arrudeia.feature.onboarding.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.domain.LibKeysUseCase
import com.arrudeia.core.location.domain.UpdateUserLastLocationUseCase
import com.arrudeia.core.result.Result
import com.arrudeia.feature.onboarding.domain.GetCurrentUserDataStoreUseCase
import com.arrudeia.feature.onboarding.domain.GetIsFirstTimeOpenUseCase
import com.arrudeia.feature.onboarding.domain.SaveIsFirstTimeOpenUseCase
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
    private val updateLastLocationUseCase: UpdateUserLastLocationUseCase,
    private val getIsFirstTimeOpenUseCase: GetIsFirstTimeOpenUseCase,
    private val saveIsFirstTimeOpenUseCase: SaveIsFirstTimeOpenUseCase
) : ViewModel() {

    fun updateLastLocation(lastCity: String, lastCountry: String) {
        viewModelScope.launch {
            updateLastLocationUseCase(lastCity, lastCountry)
        }
    }

    fun saveIsFirstTimeOpenUseCase() {
        viewModelScope.launch {
            saveIsFirstTimeOpenUseCase(false)
        }
    }

    private val _IsFirstTimeOpenUseCase = mutableStateOf(true)
    val isFirstTimeOpenUseCase: State<Boolean> = _IsFirstTimeOpenUseCase

    fun getIsFirstTimeOpen() {
        viewModelScope.launch {
           val result = getIsFirstTimeOpenUseCase()
            _IsFirstTimeOpenUseCase.value = result
        }
    }


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
            libKeysUseCase.saveLocalLibKeys()
            getCurrentUser()
        }
    }


    sealed interface CurrentUserUiState {
        data class Success(val success: Boolean = true) : CurrentUserUiState
        data object Loading : CurrentUserUiState

        data class Error(val message: Int = 0) : CurrentUserUiState
    }
}
