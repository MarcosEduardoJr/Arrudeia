package com.arrudeia.core.data.repository

import com.arrudeia.core.result.Result


interface MapKeysRepository {
    suspend fun getRemoteMapKeys(): Result<MutableMap<String, String>>
    suspend fun getLocalLibKeys(): MutableMap<String, String>
    suspend fun saveLocalLibKeys(keys: MutableMap<String, String>): Boolean

}
