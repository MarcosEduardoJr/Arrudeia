package com.arrudeia.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.result.Result
import com.arrudeia.feature.profile.data.entity.UserAboutMeEntity
import com.arrudeia.feature.profile.domain.GetUserAboutMeUseCase
import com.arrudeia.feature.profile.domain.GetUserAddressUseCase
import com.arrudeia.feature.profile.domain.UpdateUserAboutMeUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileAboutMeViewModel @Inject constructor(
    private val useCase: GetUserAddressUseCase,
    private val updateUserAboutMeUseCase: UpdateUserAboutMeUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val getUserAboutMeUseCase: GetUserAboutMeUseCase
) : ViewModel() {


    var uiStateInterestsUpdateUser: MutableStateFlow<InterestsUiState> =
        MutableStateFlow(InterestsUiState.Loading)
    val sharedFlowInterestsUpdateUser = uiStateInterestsUpdateUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = InterestsUiState.Loading
    )

    var updateStateInterestsUpdateUser: MutableStateFlow<UpdateInterestsUiState> =
        MutableStateFlow(UpdateInterestsUiState.Loading)
    val updateSharedFlowInterestsUpdateUser = updateStateInterestsUpdateUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UpdateInterestsUiState.Loading
    )


    fun getUserInterests() {
        viewModelScope.launch {
            when (val result = getUserAboutMeUseCase()) {
                is Result.Success -> {
                    uiStateInterestsUpdateUser.value = InterestsUiState.Success(
                        result.data
                    )
                }

                is Result.Error -> {
                    uiStateInterestsUpdateUser.value = InterestsUiState.Error(result.message)
                }

                else -> {}
            }
        }
    }


    fun saveInterests(interests: String, biography: String) {
        viewModelScope.launch {
            when (val result = updateUserAboutMeUseCase(interests, biography)) {
                is Result.Success -> {
                    updateStateInterestsUpdateUser.value = UpdateInterestsUiState.Success(
                        result.data
                    )
                }

                is Result.Error -> {
                    updateStateInterestsUpdateUser.value = UpdateInterestsUiState.Error(result.message)
                }

                else -> {}
            }
        }
    }


    sealed interface UpdateInterestsUiState {
        data class Success(val data: String?) : UpdateInterestsUiState
        data class Error(val message: Int?) : UpdateInterestsUiState
        data object Loading : UpdateInterestsUiState
    }

    sealed interface InterestsUiState {
        data class Success(val data: UserAboutMeEntity) : InterestsUiState
        data class Error(val message: Int?) : InterestsUiState
        data object Loading : InterestsUiState
    }

}
