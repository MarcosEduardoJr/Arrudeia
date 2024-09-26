package com.arrudeia.feature.sign.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.sign.domain.CreateUserDataStoreUseCase
import com.arrudeia.core.sign.domain.CreateUserFirebaseUseCase
import com.arrudeia.core.sign.domain.SignInUserFirebaseUseCase
import com.arrudeia.core.sign.domain.entity.SignFirebaseUserUseCaseEntity
import com.arrudeia.feature.sign.R.string.erro_sign_user
import com.arrudeia.feature.sign.R.string.sign_error_sign
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val createUserUseCase: CreateUserFirebaseUseCase,
    private val createUserDataStoreUseCase: CreateUserDataStoreUseCase,
    private val signUseCase : SignInUserFirebaseUseCase
) : ViewModel() {

    var uiState: MutableStateFlow<SignUiState> =
        MutableStateFlow(SignUiState.None)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SignUiState.None
    )

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            uiState.value = SignUiState.Loading
            val result = createUserUseCase.invoke(email, password)
            if (result != null) {
                saveLocally(result)
            } else {
                uiState.value = SignUiState.Error(
                    erro_sign_user
                )
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            uiState.value = SignUiState.Loading
            val result = signUseCase(email, password)
            if (result != null) {
                saveLocally(result)
            } else {
                uiState.value = SignUiState.Error(
                    sign_error_sign
                )
            }
        }
    }

    private suspend fun saveLocally(result: SignFirebaseUserUseCaseEntity) {
        if (createUserDataStoreUseCase.invoke(result.uid, result.name, result.email)) {
            uiState.value = SignUiState.Success()
        } else {
            uiState.value = SignUiState.Error(
                erro_sign_user
            )
        }
    }



    sealed interface SignUiState {
        data class Success(val success: Boolean = true) : SignUiState
        data class Error(val message: Int) : SignUiState
        data object Loading : SignUiState
        data object None : SignUiState
    }

}
