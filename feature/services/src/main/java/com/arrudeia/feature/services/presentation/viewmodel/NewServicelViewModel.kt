package com.arrudeia.feature.services.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.common.R.string.generic_error
import com.arrudeia.core.designsystem.component.DropListUiModel
import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.domain.CreateServiceImageUseCase
import com.arrudeia.feature.services.domain.CreateServiceUseCase
import com.arrudeia.feature.services.domain.GetAddressByZipCodeUseCase
import com.arrudeia.feature.services.domain.GetServicesExpertiseUseCase
import com.arrudeia.feature.services.domain.toServiceUserImageUiModel
import com.arrudeia.feature.services.domain.toServiceUserUseCase
import com.arrudeia.feature.services.presentation.map.toCepAddressUiModel
import com.arrudeia.feature.services.presentation.map.toEntity
import com.arrudeia.feature.services.presentation.model.CepAddressUiModel
import com.arrudeia.feature.services.presentation.model.NewServiceUserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewServiceViewModel @Inject constructor(
    private val useCase: CreateServiceUseCase,
    private val useCaseExpertise: GetServicesExpertiseUseCase,
    private val useCaseServiceImage: CreateServiceImageUseCase,
    private val useCaseCepByZipCode: GetAddressByZipCodeUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    var uiState: MutableStateFlow<NewServiceUiState> =
        MutableStateFlow(NewServiceUiState.Loading)
    val sharedFlow = uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = NewServiceUiState.Loading
    )

    var uiStateExpertise: MutableStateFlow<ServiceExpertiseUiState> =
        MutableStateFlow(ServiceExpertiseUiState.Loading)
    val sharedFlowExpertise = uiStateExpertise.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ServiceExpertiseUiState.Loading
    )

    var uiStateZipCode: MutableStateFlow<AddressUiState> =
        MutableStateFlow(AddressUiState.Loading)
    val sharedFlowZipCodee = uiStateZipCode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = AddressUiState.Loading
    )

    fun saveService(newServiceUserUiModel: NewServiceUserUiModel) {
        saveServiceImage(newServiceUserUiModel)
    }

    private fun saveServiceImage(newServiceUserUiModel: NewServiceUserUiModel) {
        viewModelScope.launch {
            newServiceUserUiModel.image = useCaseServiceImage(uri).toServiceUserImageUiModel()
            createNewService(newServiceUserUiModel)
        }
    }

    var addressByZipCode = mutableStateOf<CepAddressUiModel?>(null)
        private set

    fun getAddressByZipCode(zipCode: String) {
        viewModelScope.launch {
            when (val result = useCaseCepByZipCode(zipCode)) {
                is Result.Success -> {
                    if (result.data == null) {
                        uiStateZipCode.value = AddressUiState.Error(generic_error)
                        return@launch
                    }
                    result.data?.let {
                        addressByZipCode.value = it.toCepAddressUiModel()

                    }
                }

                is Result.Error -> {
                    uiStateZipCode.value = AddressUiState.Error(generic_error)
                }

                is Result.Loading -> {
                    uiStateZipCode.value = AddressUiState.Loading
                }
            }
        }
    }

    private fun createNewService(newServiceUserUiModel: NewServiceUserUiModel) {
        viewModelScope.launch {
            when (val result = useCase(newServiceUserUiModel.toServiceUserUseCase())) {
                is Result.Success -> {
                    if (result.data == null) {
                        uiState.value = NewServiceUiState.Error(generic_error)
                        return@launch
                    }
                    result.data?.let {
                        uiState.value = NewServiceUiState.Success(
                            item = it
                        )
                    }
                }

                is Result.Error -> {
                    uiState.value = NewServiceUiState.Error(generic_error)
                }

                is Result.Loading -> {
                    uiState.value = NewServiceUiState.Loading
                }
            }
        }
    }

    fun fetchDataExpertise() {
        viewModelScope.launch {
            when (val result = useCaseExpertise()) {
                is Result.Success -> {
                    uiStateExpertise.value = ServiceExpertiseUiState.Success(
                        list = result.data.toList().map { it.toEntity() }.toList()
                    )
                }

                is Result.Error -> {
                    uiStateExpertise.value =
                        ServiceExpertiseUiState.Error(generic_error)
                }

                is Result.Loading -> {
                    uiStateExpertise.value = ServiceExpertiseUiState.Loading
                }
            }
        }
    }

    private val _uri = mutableStateListOf<Uri>()
    val uri: List<Uri> = _uri

    fun onTakePhoto(uri: Uri) {
        _uri.add(uri)
    }

    fun removeImage(itemRemove: Uri) {
        uri.forEach { if (itemRemove == it) _uri.remove(it) }
    }
}


sealed interface NewServiceUiState {
    data class Success(val item: Int) : NewServiceUiState
    data class Error(val message: Int) : NewServiceUiState
    data object Loading : NewServiceUiState
}

sealed interface NewServiceExpertiseUiState {
    data class Success(val list: List<DropListUiModel>) : NewServiceExpertiseUiState
    data class Error(val message: Int) : NewServiceExpertiseUiState
    data object Loading : NewServiceExpertiseUiState
}

sealed interface AddressUiState {
    data class Success(val address: CepAddressUiModel) : AddressUiState
    data class Error(val message: Int) : AddressUiState
    data object Loading : AddressUiState
}

