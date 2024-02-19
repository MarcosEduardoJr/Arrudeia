package com.arrudeia.core.domain

import com.arrudeia.core.data.network.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.core.data.repository.ArrudeiaTvRepositoryEntity
import com.arrudeia.core.data.repository.TravelRepositoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertTrue

class GetAllArrudeiaTvUseCaseTest {
    private lateinit var repository: DefaultHomeTravelsRepositoryImpl
    private lateinit var useCase: GetAllArrudeiaTvUseCase

    @Before
    fun setup() {
        repository = Mockito.mock()
        useCase = GetAllArrudeiaTvUseCase(repository)
    }

    @Test
    fun `invoke use case is not empty`() = runBlocking {
        val listResult = mutableListOf(ArrudeiaTvRepositoryEntity())
        Mockito.`when`(repository.getAllArrudeiaTv()).thenReturn(listResult)
        assertTrue(useCase.invoke().isNotEmpty())
    }
}