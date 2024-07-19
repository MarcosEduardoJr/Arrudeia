package com.arrudeia.feature.sign.domain

import com.arrudeia.core.sign.domain.CreateUserDataStoreUseCase
import com.arrudeia.core.sign.data.SignDataStoreUserRepository
import com.arrudeia.feature.sign.data.entity.SignDataStoreUserRepositoryEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CreateUserDataStoreUseCaseTest {

    private lateinit var repository: SignDataStoreUserRepository
    private lateinit var useCase: CreateUserDataStoreUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = CreateUserDataStoreUseCase(repository)
    }

    @Test
    fun `invoke calls repository saveUser`() = runBlocking {
        val uid = "testUid"
        val name = "testName"
        val email = "testEmail"

        coEvery { repository.saveUser(SignDataStoreUserRepositoryEntity(uid, name, email)) } returns true

        val result = useCase(uid, name, email)

        Assert.assertTrue(result)
    }
}