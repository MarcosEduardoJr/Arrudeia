package com.arrudeia.core.domain

import com.arrudeia.core.data.network.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.core.data.entity.TravelRepositoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import kotlin.test.assertTrue

class GetAllTravelHomeUseCaseTest {
    private lateinit var repository: DefaultHomeTravelsRepositoryImpl
    private lateinit var useCase: GetAllTravelHomeUseCase

    @Before
    fun setup() {
        repository = Mockito.mock()
        useCase = GetAllTravelHomeUseCase(repository)
    }

    @Test
    fun `invoke use case is not empty`() = runBlocking {
        val listResult = mutableListOf(TravelRepositoryEntity())
        `when`(repository.getAllTravels()).thenReturn(listResult)
        assertTrue(useCase.invoke().isNotEmpty())
    }
}