package com.arrudeia.core.sign.data

import androidx.annotation.VisibleForTesting
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arrudeia.feature.sign.data.entity.SignDataStoreUserRepositoryEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class SignDataStoreUserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SignDataStoreUserRepository {

    companion object {
        @VisibleForTesting
        val UID_KEY = stringPreferencesKey("uid")

        @VisibleForTesting
        val NAME_KEY = stringPreferencesKey("name")

        @VisibleForTesting
        val EMAIL_KEY = stringPreferencesKey("email")

        @VisibleForTesting
        val IMAGE_USER_KEY = stringPreferencesKey("image_user")

        @VisibleForTesting
        val IS_SAVED_DOC = booleanPreferencesKey("is_saved_doc")
    }


    override suspend fun saveUser(user: SignDataStoreUserRepositoryEntity): Boolean {



        dataStore.edit { preferences ->
            preferences[UID_KEY] = user.uid
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[IMAGE_USER_KEY] = user.image
            preferences[IS_SAVED_DOC] = user.isSavedDoc
        }
        return dataStore.data.first().contains(UID_KEY)
    }
}


