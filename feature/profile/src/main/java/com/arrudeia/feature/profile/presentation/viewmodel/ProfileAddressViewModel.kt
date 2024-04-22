package com.arrudeia.feature.profile.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.feature.profile.domain.GetUserAddressUseCase
import com.arrudeia.core.domain.UpdateUserAddressUseCase
import com.arrudeia.core.entity.UserAddressUseCaseEntity
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.presentation.model.ProfileAddressUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileAddressViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: GetUserAddressUseCase,
    private val updateUseCase: UpdateUserAddressUseCase
) : ViewModel() {

    var uuidCurrentUser = ""

    var uiState: MutableStateFlow<AddressUiState> =
        MutableStateFlow(AddressUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = AddressUiState.Loading
    )


    var uiStateUpdateUser: MutableStateFlow<PersonalInformationUpdateUserUiState> =
        MutableStateFlow(PersonalInformationUpdateUserUiState.Loading)
    val sharedFlowUpdateUser = uiStateUpdateUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PersonalInformationUpdateUserUiState.Loading
    )


    fun saveAddress(
         zipCode: String?,
         street: String?,
         number: Int?,
         district: String?,
         city: String?,
         state: String?,
         country: String?,
    ) {
        viewModelScope.launch {

            val result = updateUseCase(
                UserAddressUseCaseEntity(
                    uuid = uuidCurrentUser,
                    zipCode = zipCode,
                    street = street,
                    number = number,
                    district = district,
                    city = city,
                    state = state,
                    country = country
            ))

            if (!result.contentEquals(uuidCurrentUser))
                uiStateUpdateUser.value =
                    PersonalInformationUpdateUserUiState.Error(R.string.error_update_user)
            else
                uiStateUpdateUser.value =
                    PersonalInformationUpdateUserUiState.Success(R.string.success_saved_user)
        }
    }

    fun getUserPersonalInformation() {
        viewModelScope.launch {
            val result = useCase()
            if (result == null)
                uiState.value = AddressUiState.Error(R.string.error_get_user)
            else {
                uiState.value = AddressUiState.Success(
                    result.toUiModel()
                )
                uuidCurrentUser = result.uuid.orEmpty()
            }
        }
    }

    private fun UserAddressUseCaseEntity?.toUiModel(): ProfileAddressUiModel {
        var result: ProfileAddressUiModel
        this.let {
            result = ProfileAddressUiModel(
                uuid = this?.uuid,
                zipCode = this?.zipCode.orEmpty(),
                street = this?.street.orEmpty(),
                number = this?.number,
                district = this?.district.orEmpty(),
                city = this?.city.orEmpty(),
                state = this?.state.orEmpty(),
                country = this?.country.orEmpty()
            )
        }
        return result
    }



    sealed interface AddressUiState {
        data class Success(val data: ProfileAddressUiModel) : AddressUiState
        data class Error(val message: Int) : AddressUiState
        data object Loading : AddressUiState
    }

    sealed interface PersonalInformationUpdateUserUiState {
        data class Success(val message: Int) : PersonalInformationUpdateUserUiState
        data class Error(val message: Int) : PersonalInformationUpdateUserUiState
        data object Loading : PersonalInformationUpdateUserUiState
    }

}
