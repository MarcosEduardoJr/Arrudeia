package com.arrudeia.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.data.repository.map.toMapKeyRepositoryEntity
import com.arrudeia.core.graphql.GetKeysQuery
import com.arrudeia.core.result.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class MapKeysRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val dataStore: DataStore<Preferences>
) : MapKeysRepository {
    override suspend fun getRemoteMapKeys(): Result<MutableMap<String, String>> {
        val response = apolloClient.query(GetKeysQuery()).execute()
        if (response.hasErrors() || response.data?.keys?.toMapKeyRepositoryEntity() == null)
            return Result.Error(null)
        return Result.Success(response.data!!.keys!!.toMapKeyRepositoryEntity())
    }

    companion object {
        private val LIB_NAME = stringPreferencesKey("lib_name")
        private val LIB_KEY = stringPreferencesKey("lib_key")
        private val SERPAPI_KEY = "SERPAPI_KEY"
        private val MAPS_API_KEY = "MAPS_API_KEY"
    }

    override suspend fun getLocalLibKeys(): MutableMap<String, String> {
        var mapKeys: MutableMap<String, String> = mutableMapOf()
        dataStore.data.map { preferences ->
            if (preferences[LIB_NAME].contentEquals(SERPAPI_KEY)
                || preferences[LIB_NAME].contentEquals(MAPS_API_KEY)
            ) {
                val name = preferences[LIB_NAME] ?: return@map null
                val key = preferences[LIB_KEY] ?: return@map null
                mapKeys[name] = key
            } else {
                return@map null
            }
        }.firstOrNull()
        return mapKeys
    }

    override suspend fun saveLocalLibKeys(keys: MutableMap<String, String>): Boolean {
        dataStore.edit { preferences ->
            keys.forEach { (name, key) ->
                preferences[LIB_NAME] = name
                preferences[LIB_KEY] = key
            }
        }
        return dataStore.data.first().contains(LIB_NAME)
    }
}


