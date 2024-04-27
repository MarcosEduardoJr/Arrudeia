package com.arrudeia.core.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {

    data class Success<T>(val data: T) : Result<T>
    data class Error(val message: Int?) : Result<Nothing>
    data object Loading : Result<Nothing>
}

