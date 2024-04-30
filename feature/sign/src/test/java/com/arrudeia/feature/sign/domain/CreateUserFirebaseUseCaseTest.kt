package com.arrudeia.feature.sign.domain

import com.arrudeia.feature.sign.data.SignFirebaseUserRepositoryImpl
import com.arrudeia.feature.sign.data.entity.SignFirebaseUserRepositoryEntity
import com.arrudeia.feature.sign.domain.entity.SignFirebaseUserUseCaseEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CreateUserFirebaseUseCaseTest {

    private lateinit var repository: SignFirebaseUserRepositoryImpl
    private lateinit var useCase: CreateUserFirebaseUseCase

    @Before
    fun setup() {
        repository = mock(SignFirebaseUserRepositoryImpl::class.java)
        useCase = CreateUserFirebaseUseCase(repository)
    }

    @Test
    fun `invoke with valid email and password returns non-null entity`() = runBlocking {
        val email = "test@test.com"
        val password = "password"
        val entity = SignFirebaseUserRepositoryEntity("1", "Test User", email)

        `when`(repository.createUserWithEmailAndPassword(email, password)).thenReturn(entity)

        val result = useCase.invoke(email, password)

        assertNotNull(result)
        assertEquals("1", result?.uid)
        assertEquals("Test User", result?.name)
        assertEquals(email, result?.email)
    }

    @Test
    fun `invoke with invalid email and password returns null entity`() = runBlocking {
        val email = "invalid@test.com"
        val password = "password"

        `when`(repository.createUserWithEmailAndPassword(email, password)).thenReturn(null)

        val result = useCase.invoke(email, password)

        assertNull(result)
    }
}