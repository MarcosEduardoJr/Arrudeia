package com.arrudeia.core.domain

import com.arrudeia.feature.home.data.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.feature.home.data.entity.ArrudeiaTvRepositoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertTrue

class GetAllArrudeiaTvUseCaseTest {
    private lateinit var repository: com.arrudeia.feature.home.data.DefaultHomeTravelsRepositoryImpl
    private lateinit var useCase: com.arrudeia.feature.home.domain.GetAllArrudeiaTvUseCase

    @Before
    fun setup() {
        repository = Mockito.mock()
        useCase = com.arrudeia.feature.home.domain.GetAllArrudeiaTvUseCase(repository)
    }

    @Test
    fun `invoke use case is not empty`() = runBlocking {
        val listResult = mutableListOf(com.arrudeia.feature.home.data.entity.ArrudeiaTvRepositoryEntity())
        Mockito.`when`(repository.getAllArrudeiaTv()).thenReturn(listResult)
        assertTrue(useCase.invoke().isNotEmpty())
    }
}