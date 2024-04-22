package com.arrudeia.core.domain

import com.arrudeia.core.data.network.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.core.data.entity.TravelRepositoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertTrue

class GetTravelByIdUseCaseTest {

    private lateinit var repository: DefaultHomeTravelsRepositoryImpl
    private lateinit var useCase: com.arrudeia.feature.trip.domain.GetTravelByIdUseCase

    @Before
    fun setup() {
        repository = Mockito.mock()
        useCase = com.arrudeia.feature.trip.domain.GetTravelByIdUseCase(repository)
    }

    @Test
    fun `invoke use case is not null`() = runBlocking {
        val result = TravelRepositoryEntity()
        Mockito.`when`(repository.getTravelById(0)).thenReturn(result)
        assertTrue(useCase.invoke(0)!=null)
    }
}