package com.arrudeia.core.domain

import com.arrudeia.core.data.repository.UserDataStoreUserRepositoryImpl
import com.arrudeia.core.result.Result.Success
import javax.inject.Inject

class IsSavedIdDocUserDataStoreUseCase @Inject constructor(
    private val repository: UserDataStoreUserRepositoryImpl,
) {
    suspend operator fun invoke(
    ): Boolean {
        return when (val result = repository.getUserData()) {
            is Success -> result.data?.isSavedDoc == true
            else -> false
        }
    }
}
