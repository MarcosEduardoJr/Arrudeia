package com.arrudeia.feature.sign.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.feature.sign.R.string.erro_sign_user
import com.arrudeia.feature.sign.R.string.sign_error_sign
import com.arrudeia.feature.sign.domain.CreateUserDataStoreUseCase
import com.arrudeia.feature.sign.domain.CreateUserFirebaseUseCase
import com.arrudeia.feature.sign.domain.SignInUserFirebaseUseCase
import com.arrudeia.feature.sign.domain.entity.SignFirebaseUserUseCaseEntity
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
        MutableStateFlow(SignUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SignUiState.Loading
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
            val result = signUseCase.invoke(email, password)
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
    }

}
