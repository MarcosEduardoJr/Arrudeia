package com.arrudeia.core.data.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arrudeia.core.data.repository.DataStoreUserRepository
import com.arrudeia.core.data.entity.DataStoreUserRepositoryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreUserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreUserRepository {

    companion object {
        private val UID_KEY = stringPreferencesKey("uid")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val IMAGE_USER_KEY = stringPreferencesKey("image_user")
    }

    override suspend fun getUserData(): DataStoreUserRepositoryEntity? {
        return dataStore.data.map { preferences ->
            val uid = preferences[UID_KEY] ?: return@map null
            val name = preferences[NAME_KEY] ?: return@map null
            val email = preferences[EMAIL_KEY] ?: return@map null
            val image = preferences[IMAGE_USER_KEY] ?: return@map null
            DataStoreUserRepositoryEntity(uid, name, email,image)
        }.firstOrNull()
    }

    override suspend fun saveUser(user: DataStoreUserRepositoryEntity): Boolean {
        dataStore.edit { preferences ->
            preferences[UID_KEY] = user.uid
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[IMAGE_USER_KEY] = user.image
        }
        return dataStore.data.first().contains(UID_KEY)
    }
}


