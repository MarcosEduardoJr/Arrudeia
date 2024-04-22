package com.arrudeia.core.domain

import com.arrudeia.feature.home.data.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.core.data.entity.TravelRepositoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import kotlin.test.assertTrue

class GetAllTravelHomeUseCaseTest {
    private lateinit var repository: com.arrudeia.feature.home.data.DefaultHomeTravelsRepositoryImpl
    private lateinit var useCase: com.arrudeia.feature.home.domain.GetAllTravelHomeUseCase

    @Before
    fun setup() {
        repository = Mockito.mock()
        useCase = com.arrudeia.feature.home.domain.GetAllTravelHomeUseCase(repository)
    }

    @Test
    fun `invoke use case is not empty`() = runBlocking {
        val listResult = mutableListOf(TravelRepositoryEntity())
        `when`(repository.getAllTravels()).thenReturn(listResult)
        assertTrue(useCase.invoke().isNotEmpty())
    }
}