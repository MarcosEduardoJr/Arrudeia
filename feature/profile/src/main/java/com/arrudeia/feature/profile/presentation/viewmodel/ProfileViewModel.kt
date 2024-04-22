package com.arrudeia.feature.profile.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.feature.profile.domain.GetUserPersonalInformationUseCase
import com.arrudeia.feature.profile.domain.LogoutCurrentUserDataStoreUseCase
import com.arrudeia.feature.profile.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.presentation.model.ProfileUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: GetUserPersonalInformationUseCase,
    private val logoutCurrentUserDataStoreUseCase: LogoutCurrentUserDataStoreUseCase
) : ViewModel() {

    var uiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(ProfileUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ProfileUiState.Loading
    )

    fun getUserPersonalInformation() {
        viewModelScope.launch {
            val result = useCase()
            if (result == null)
                uiState.value =
                    ProfileUiState.Error(R.string.error_get_user)
            else {
                uiState.value =
                    ProfileUiState.Success(
                        result.toUiModel()
                    )
            }
        }
    }

    private fun UserPersonalInformationUseCaseEntity.toUiModel(): ProfileUiModel {
        var result: ProfileUiModel
        this.let {
            result = ProfileUiModel(
                name = it.name,
                email = it.email,
                image = it.profileImage
            )
        }
        return result
    }

    fun logout() {
        viewModelScope.launch {
            logoutCurrentUserDataStoreUseCase()
        }
    }

    sealed interface ProfileUiState {
        data class Success(val data: ProfileUiModel) : ProfileUiState
        data class Error(val message: Int) : ProfileUiState
        data object Loading : ProfileUiState
    }
}
