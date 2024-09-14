package com.arrudeia.core.domain

import com.arrudeia.core.data.repository.MapKeysRepositoryImpl
import com.arrudeia.core.result.Result.Success
import javax.inject.Inject

class LibKeysUseCase @Inject constructor(
    private val repository: MapKeysRepositoryImpl,
) {
    suspend fun saveLocalLibKeys(): Boolean {
        if (repository.getLocalLibKeys().isEmpty()) {
            when (val mapKeys = repository.getRemoteMapKeys()) {
                is Success -> {
                    if (mapKeys.data.isNotEmpty())
                        return repository.saveLocalLibKeys(mapKeys.data)
                    return false
                }

                else -> {
                    return false
                }
            }
        }
        return false
    }

    suspend fun fetchLocalLibKeys() =
        repository.getLocalLibKeys()
}
