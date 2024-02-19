package com.arrudeia.core.domain

import com.arrudeia.core.data.network.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.core.data.repository.TravelRepositoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertTrue

class GetTravelByIdUseCaseTest {

    private lateinit var repository: DefaultHomeTravelsRepositoryImpl
    private lateinit var useCase: GetTravelByIdUseCase

    @Before
    fun setup() {
        repository = Mockito.mock()
        useCase = GetTravelByIdUseCase(repository)
    }

    @Test
    fun `invoke use case is not null`() = runBlocking {
        val result = TravelRepositoryEntity()
        Mockito.`when`(repository.getTravelById(0)).thenReturn(result)
        assertTrue(useCase.invoke(0)!=null)
    }
}