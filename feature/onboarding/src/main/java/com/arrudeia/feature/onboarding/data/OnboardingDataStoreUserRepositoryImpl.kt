package com.arrudeia.feature.onboarding.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arrudeia.feature.onboarding.data.entity.OnboardingDataStoreUserRepositoryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.arrudeia.core.result.Result
import com.arrudeia.feature.onboarding.R

class OnboardingDataStoreUserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : OnboardingDataStoreUserRepository {

    companion object {
        private val UID_KEY = stringPreferencesKey("uid")
    }

    override suspend fun isUserSaved(): Result<Boolean?> {
        val isUidSaved = dataStore.data.map { preferences ->
            val uid = preferences[UID_KEY] ?: return@map null
            uid
        }.firstOrNull()
        return if (isUidSaved != null)
            Result.Success(true)
        else
            Result.Error(null)
    }
}


