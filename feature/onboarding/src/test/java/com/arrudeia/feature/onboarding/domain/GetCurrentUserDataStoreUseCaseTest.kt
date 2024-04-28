package com.arrudeia.feature.onboarding.domain

import com.arrudeia.feature.onboarding.data.OnboardingDataStoreUserRepository
import com.arrudeia.core.result.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetCurrentUserDataStoreUseCaseTest {

    private lateinit var repository: OnboardingDataStoreUserRepository
    private lateinit var useCase: GetCurrentUserDataStoreUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetCurrentUserDataStoreUseCase(repository)
    }

    @Test
    fun `invoke returns Success when user is saved`() = runBlocking {
        coEvery { repository.isUserSaved() } returns Result.Success(true)

        val result = useCase()

        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(true, (result as Result.Success).data)
    }

    @Test
    fun `invoke returns Error when user is not saved`() = runBlocking {
        coEvery { repository.isUserSaved() } returns Result.Error(null)

        val result = useCase()

        Assert.assertTrue(result is Result.Error)
    }
}