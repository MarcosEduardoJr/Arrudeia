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


class OnboardingDataStoreUserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : com.arrudeia.feature.onboarding.data.OnboardingDataStoreUserRepository {

    companion object {
        private val UID_KEY = stringPreferencesKey("uid")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val IMAGE_USER_KEY = stringPreferencesKey("image_user")
    }

    override suspend fun getUserData():  OnboardingDataStoreUserRepositoryEntity? {
        return dataStore.data.map { preferences ->
            val uid = preferences[UID_KEY] ?: return@map null
            val name = preferences[NAME_KEY] ?: return@map null
            val email = preferences[EMAIL_KEY] ?: return@map null
            val image = preferences[IMAGE_USER_KEY] ?: return@map null
            OnboardingDataStoreUserRepositoryEntity(
                uid,
                name,
                email,
                image
            )
        }.firstOrNull()
    }

    override suspend fun logoutUser() {
        dataStore.edit { preferences ->
            preferences.remove(UID_KEY)
            preferences.remove(NAME_KEY)
            preferences.remove(EMAIL_KEY)
            preferences.remove(IMAGE_USER_KEY)
        }
    }

    override suspend fun saveUser(user:  OnboardingDataStoreUserRepositoryEntity): Boolean {
        dataStore.edit { preferences ->
            preferences[UID_KEY] = user.uid
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[IMAGE_USER_KEY] = user.image
        }
        return dataStore.data.first().contains(UID_KEY)
    }
}


