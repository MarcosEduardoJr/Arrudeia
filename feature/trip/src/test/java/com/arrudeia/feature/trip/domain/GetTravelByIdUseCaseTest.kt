package com.arrudeia.feature.trip.domain

import com.arrudeia.feature.trip.data.TripTravelRepositoryImpl
import com.arrudeia.feature.trip.data.entity.TravelRepositoryEntity
import com.arrudeia.feature.trip.domain.entity.TravelUseCaseEntity
import com.arrudeia.core.result.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import com.arrudeia.feature.trip.R

class GetTravelByIdUseCaseTest {

    private lateinit var repository: TripTravelRepositoryImpl
    private lateinit var useCase: GetTravelByIdUseCase

    @Before
    fun setup() {
        repository = Mockito.mock(TripTravelRepositoryImpl::class.java)
        useCase = GetTravelByIdUseCase(repository)
    }

    @Test
    fun `test invoke with valid id`() = runBlocking {
        val id = 1L
        val entity = TravelRepositoryEntity(
            id = id,
            name = "Test",
            city = "Test City",
            state = "Test State",
            day = 1,
            month = 1,
            year = 2022,
            price = 100,
            discount = 10,
            cover_image_url = "Test URL",
            whatsapp = "Test Whatsapp",
            description = "Test Description",
            include = listOf(),
            optional = listOf()
        )
        `when`(repository.getTravelById(id)).thenReturn(Result.Success(entity))

        val result = useCase.invoke(id)

        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(id, (result as Result.Success).data?.id)
    }

    @Test
    fun `test invoke with invalid id`() = runBlocking {
        val id = -1L
        `when`(repository.getTravelById(id)).thenReturn(Result.Error(R.string.erro_message_list_travels))

        val result = useCase.invoke(id)

        Assert.assertTrue(result is Result.Error)
    }
}