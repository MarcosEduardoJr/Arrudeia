package com.arrudeia.core.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.domain.UpdateUserPersonalInformationUseCase
import com.arrudeia.core.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentAnalysisViewModel @Inject constructor(
    private val useCase: UpdateUserPersonalInformationUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {



    private val _uri = mutableStateListOf<Uri>()
    val uri: List<Uri> = _uri

    fun onTakePhoto(uri: Uri) {
        _uri.add(uri)
    }

    fun removeImage(itemRemove: Uri) {
        uri.forEach { if (itemRemove == it) _uri.remove(it) }
    }

    fun clearImages() {
        _uri.clear()
    }

    var isLoading = mutableStateOf(false)
        private set
    var toastMessage = mutableStateOf(0)
        private set
    var savedDocument = mutableStateOf(false)
        private set

    fun saveDocument() {
        isLoading.value = true
        viewModelScope.launch {
            when (val response = useCase(_uri)) {
                is Result.Loading -> {
                    isLoading.value = true
                }

                is Result.Success -> {
                    isLoading.value = false
                    savedDocument.value = true
                }

                is Result.Error -> {
                    isLoading.value = false
                    toastMessage.value = response.message ?: 0
                }

                is Result.ErrorMessage -> TODO()
            }
        }
    }

    fun reset() {
        isLoading.value = false
        toastMessage.value = 0
        savedDocument.value = false
        clearImages()
    }
}


