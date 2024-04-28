package com.arrudeia.feature.onboarding.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arrudeia.core.result.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class OnboardingDataStoreUserRepositoryImplTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var repository: OnboardingDataStoreUserRepositoryImpl

    @Before
    fun setup() {
        dataStore = mockk()
        repository = OnboardingDataStoreUserRepositoryImpl(dataStore)
    }

    @Test
    fun `isUserSaved returns Success when uid is present`() = runBlocking {
        val uidKey = stringPreferencesKey("uid")
        coEvery { dataStore.data } returns flowOf(preferencesOf(uidKey to "testUid"))

        val result = repository.isUserSaved()

        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(true, (result as Result.Success).data)
    }

    @Test
    fun `isUserSaved returns Error when uid is absent`() = runBlocking {
        coEvery { dataStore.data } returns flowOf(preferencesOf())

        val result = repository.isUserSaved()

        Assert.assertTrue(result is Result.Error)
    }
}