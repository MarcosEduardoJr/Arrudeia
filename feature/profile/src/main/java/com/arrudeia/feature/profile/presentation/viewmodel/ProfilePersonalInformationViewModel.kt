package com.arrudeia.feature.profile.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.result.Result
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.domain.GetUserPersonalInformationUseCase
import com.arrudeia.feature.profile.domain.UpdateUserPersonalInformationUseCase
import com.arrudeia.feature.profile.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.feature.profile.presentation.model.ProfilePersonalInformationUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePersonalInformationViewModel @Inject constructor(
    private val useCase: GetUserPersonalInformationUseCase,
    private val updateUseCase: UpdateUserPersonalInformationUseCase
) : ViewModel() {

    var uuidCurrentUser = ""

    var uiState: MutableStateFlow<PersonalInformationUiState> =
        MutableStateFlow(PersonalInformationUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PersonalInformationUiState.Loading
    )

    var uiStateUpdateUser: MutableStateFlow<PersonalInformationUpdateUserUiState> =
        MutableStateFlow(PersonalInformationUpdateUserUiState.Loading)
    val sharedFlowUpdateUser = uiStateUpdateUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PersonalInformationUpdateUserUiState.Loading
    )

    fun savePersonalInformation(
        nameValue: String,
        docIdValue: String,
        emailValue: String,
        phoneValue: String,
        birthDateValue: String,
        gender: String
    ) {
        viewModelScope.launch {

            when (val result = updateUseCase(
                UserPersonalInformationUseCaseEntity(
                    uuid = uuidCurrentUser,
                    name = nameValue,
                    idDocument = docIdValue,
                    email = emailValue,
                    phone = phoneValue,
                    birthDate = birthDateValue,
                    gender = gender
                ), uri.value
            )) {
                is Result.Success -> {
                    uiStateUpdateUser.value =
                        PersonalInformationUpdateUserUiState.Success(R.string.success_saved_user)
                }

                is Result.Error -> {
                    uiStateUpdateUser.value =
                        PersonalInformationUpdateUserUiState.Error(R.string.error_update_user)
                }

                else -> {
                    uiStateUpdateUser.value =
                        PersonalInformationUpdateUserUiState.Error(R.string.error_update_user)
                }
            }
        }
    }

    fun getUserPersonalInformation() {
        viewModelScope.launch {
            when (val result = useCase()) {
                is Result.Success -> {
                    uiState.value = PersonalInformationUiState.Success(
                        result.data.toUiModel()
                    )
                    uuidCurrentUser = result.data.uuid.orEmpty()
                }

                is Result.Error -> {
                    uiState.value = PersonalInformationUiState.Error(R.string.error_get_user)
                }

                else -> {
                    uiState.value = PersonalInformationUiState.Error(R.string.error_get_user)
                }
            }

        }
    }

    private fun UserPersonalInformationUseCaseEntity.toUiModel(): ProfilePersonalInformationUiModel {
        var result: ProfilePersonalInformationUiModel
        this.let {
            result = ProfilePersonalInformationUiModel(
                uuid = it.uuid,
                name = it.name,
                email = it.email,
                phone = it.phone,
                idDocument = it.idDocument,
                birthDate = it.birthDate,
                profileImage = it.profileImage,
                gender = it.gender
            )
        }
        return result
    }

    private val _uri = MutableLiveData<Uri?>()
    val uri: LiveData<Uri?> = _uri

    fun onTakePhoto(uri: Uri) {
        _uri.value = uri
    }

    sealed interface PersonalInformationUiState {
        data class Success(val data: ProfilePersonalInformationUiModel) : PersonalInformationUiState
        data class Error(val message: Int) : PersonalInformationUiState
        data object Loading : PersonalInformationUiState
    }

    sealed interface PersonalInformationUpdateUserUiState {
        data class Success(val message: Int) : PersonalInformationUpdateUserUiState
        data class Error(val message: Int) : PersonalInformationUpdateUserUiState
        data object Loading : PersonalInformationUpdateUserUiState
    }
}
