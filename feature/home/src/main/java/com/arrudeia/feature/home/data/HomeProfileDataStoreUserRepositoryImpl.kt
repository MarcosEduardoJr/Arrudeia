package com.arrudeia.feature.home.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arrudeia.core.result.Result
import com.arrudeia.feature.home.R
import com.arrudeia.feature.home.data.entity.ProfileDataStoreUserRepositoryEntity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.arrudeia.core.common.R.string.generic_error

class HomeProfileDataStoreUserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : HomeProfileDataStoreUserRepository {

    companion object {
        private val UID_KEY = stringPreferencesKey("uid")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val IMAGE_USER_KEY = stringPreferencesKey("image_user")
        private val IS_SAVED_DOC = booleanPreferencesKey("is_saved_doc")
    }

    override suspend fun getUserData(): Result<ProfileDataStoreUserRepositoryEntity?> {
        val result = dataStore.data.map { preferences ->
            val uid = preferences[UID_KEY] ?: return@map null
            val name = preferences[NAME_KEY] ?: return@map null
            val email = preferences[EMAIL_KEY] ?: return@map null
            val image = preferences[IMAGE_USER_KEY] ?: return@map null
            val isSavedDoc = preferences[IS_SAVED_DOC] ?: return@map null
            ProfileDataStoreUserRepositoryEntity(
                uid,
                name,
                email,
                image,
                isSavedDoc
            )
        }.firstOrNull()
        return if (result != null) Result.Success(result) else Result.Error(generic_error)
    }

}


