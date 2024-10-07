package com.arrudeia.core.result

sealed interface Result<out T> {

    data class Success<T>(val data: T) : Result<T>
    data class Error(val message: Int?) : Result<Nothing>
    data class ErrorMessage(val messageError: String?) : Result<Nothing>
    data object Loading : Result<Nothing>
}

