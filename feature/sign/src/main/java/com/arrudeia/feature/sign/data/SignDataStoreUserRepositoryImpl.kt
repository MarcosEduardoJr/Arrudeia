package com.arrudeia.feature.sign.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arrudeia.feature.sign.data.entity.SignDataStoreUserRepositoryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SignDataStoreUserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) :  SignDataStoreUserRepository {

    companion object {
        private val UID_KEY = stringPreferencesKey("uid")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val IMAGE_USER_KEY = stringPreferencesKey("image_user")
    }


    override suspend fun saveUser(user:  SignDataStoreUserRepositoryEntity): Boolean {
        dataStore.edit { preferences ->
            preferences[UID_KEY] = user.uid
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[IMAGE_USER_KEY] = user.image
        }
        return dataStore.data.first().contains(UID_KEY)
    }
}


