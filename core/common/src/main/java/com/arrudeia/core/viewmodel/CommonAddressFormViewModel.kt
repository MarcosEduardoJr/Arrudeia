package com.arrudeia.core.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.common.R
import com.arrudeia.core.domain.GetAddressByZipCodeUseCase
import com.arrudeia.core.result.Result
import com.arrudeia.core.ui.model.CepAddressUiModel
import com.arrudeia.core.ui.map.toCepAddressUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonAddressFormViewModel @Inject constructor(
    private val useCaseCepByZipCode: GetAddressByZipCodeUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var uiStateZipCode: MutableStateFlow<AddressUiState> =
        MutableStateFlow(AddressUiState.Loading)
    val sharedFlowZipCodee = uiStateZipCode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = AddressUiState.Loading
    )

    var addressByZipCode = mutableStateOf<CepAddressUiModel?>(null)
        private set

    fun getAddressByZipCode(zipCode: String) {
        viewModelScope.launch {
            when (val result = useCaseCepByZipCode(zipCode)) {
                is Result.Success -> {
                    if (result.data == null) {
                        uiStateZipCode.value =
                            AddressUiState.Error(R.string.erro_message_list_travels)
                        return@launch
                    }
                    result.data?.let {
                        addressByZipCode.value = it.toCepAddressUiModel()

                    }
                }

                is Result.Error -> {
                    uiStateZipCode.value = AddressUiState.Error(R.string.erro_message_list_travels)
                }

                is Result.Loading -> {
                    uiStateZipCode.value = AddressUiState.Loading
                }
            }
        }
    }


}


sealed interface AddressUiState {
    data class Success(val address: CepAddressUiModel) : AddressUiState
    data class Error(val message: Int) : AddressUiState
    data object Loading : AddressUiState
}

