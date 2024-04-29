package com.arrudeia.feature.sign.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import com.arrudeia.feature.sign.data.entity.SignDataStoreUserRepositoryEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SignDataStoreUserRepositoryImplTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var repository: SignDataStoreUserRepositoryImpl

    @Before
    fun setup() {
        dataStore = mockk(relaxed = true)
        repository = SignDataStoreUserRepositoryImpl(dataStore)
    }

    private val transformMutablePreferences: suspend (MutablePreferences) -> Unit = { mutable -> }

    @Test
    fun `saveUser saves user to data store`() = runBlocking {
        val user = SignDataStoreUserRepositoryEntity(
            uid = "testUid",
            name = "testName",
            email = "testEmail",
            image = "testImage"
        )

        val preferences = preferencesOf(
            SignDataStoreUserRepositoryImpl.UID_KEY to user.uid,
            SignDataStoreUserRepositoryImpl.NAME_KEY to user.name,
            SignDataStoreUserRepositoryImpl.EMAIL_KEY to user.email,
            SignDataStoreUserRepositoryImpl.IMAGE_USER_KEY to user.image
        )

        coEvery { dataStore.edit(transformMutablePreferences) } coAnswers {
            val editor = firstArg<(MutablePreferences) -> Preferences>()
            editor(preferences.toMutablePreferences())
        }
        val flowPref = flowOf(preferences)
        coEvery { dataStore.data } returns flowPref

        val result = repository.saveUser(user)

        Assert.assertTrue(result)
    }
}