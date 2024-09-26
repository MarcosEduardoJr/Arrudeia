package com.arrudeia.feature.onboarding.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arrudeia.core.result.Result
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnboardingDataStoreUserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : OnboardingDataStoreUserRepository {

    companion object {
        private val UID_KEY = stringPreferencesKey("uid")
        private val IS_FIRST_TIME_OPEN = booleanPreferencesKey("is_first_time_open")
    }

    override suspend fun isUserSaved(): Result<Boolean?> {
        val isUidSaved = dataStore.data.map { preferences ->
            val uid = preferences[UID_KEY] ?: return@map null
            uid
        }.firstOrNull()
        return if (isUidSaved != null)
            Result.Success(false)
        else
            Result.Error(null)
    }


    override suspend fun isFirstTimeOpen(): Boolean {
        val isFirstTimeOpen = dataStore.data.map { preferences ->
            val result = preferences[IS_FIRST_TIME_OPEN] ?: return@map null
            result
        }.firstOrNull()
        return isFirstTimeOpen ?: true
    }

    override suspend fun saveIsFirstTimeOpen(isFirstTimeOpen: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_FIRST_TIME_OPEN] = isFirstTimeOpen
        }
    }
}


