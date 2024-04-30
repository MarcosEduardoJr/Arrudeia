package com.arrudeia.feature.sign.domain

import com.arrudeia.feature.sign.data.SignFirebaseUserRepositoryImpl
import com.arrudeia.feature.sign.data.entity.SignFirebaseUserRepositoryEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SignInUserFirebaseUseCaseTest {

    private lateinit var repository: SignFirebaseUserRepositoryImpl
    private lateinit var useCase: SignInUserFirebaseUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = SignInUserFirebaseUseCase(repository)
    }

    @Test
    fun `invoke calls repository signUserWithEmailAndPassword and maps result`() = runBlocking {
        val email = "testEmail"
        val password = "testPassword"
        val uid = "testUid"
        val name = "testName"

        coEvery { repository.signUserWithEmailAndPassword(email, password) } returns SignFirebaseUserRepositoryEntity(uid, name, email)

        val result = useCase(email, password)

        Assert.assertEquals(uid, result?.uid)
        Assert.assertEquals(name, result?.name)
        Assert.assertEquals(email, result?.email)
    }
}