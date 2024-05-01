package com.arrudeia.feature.home.domain

import com.arrudeia.feature.home.data.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.feature.home.data.entity.TravelRepositoryEntity
import com.arrudeia.feature.home.domain.entity.TravelUseCaseEntity
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*

class GetAllTravelHomeUseCaseTest {

    private val repository = mock(DefaultHomeTravelsRepositoryImpl::class.java)
    private val useCase = GetAllTravelHomeUseCase(repository)

    @Test
    fun `invoke calls repository`() = runBlocking {
        val travelList = listOf(
            TravelRepositoryEntity(
                id = 0,
                name = "testName1",
                city = "testCity1",
                state = "testState1",
                day = 1,
                month = 1,
                year = 2022,
                price = 100,
                discount = 10,
                cover_image_url = "testCoverImageUrl1",
                whatsapp = "testWhatsapp1",
                description = "testDescription1"
            ),
            TravelRepositoryEntity(
                id = 1,
                name = "testName2",
                city = "testCity2",
                state = "testState2",
                day = 2,
                month = 2,
                year = 2023,
                price = 200,
                discount = 20,
                cover_image_url = "testCoverImageUrl2",
                whatsapp = "testWhatsapp2",
                description = "testDescription2"
            )
        )

        `when`(repository.getAllTravels()).thenReturn(travelList)

        val result = useCase()

        verify(repository).getAllTravels()
        assertEquals(2, result.size)
        assertEquals(0, result[0].id)
        assertEquals("testName1", result[0].name)
        assertEquals("testCity1", result[0].city)
        assertEquals("testState1", result[0].state)
        assertEquals(1, result[0].day)
        assertEquals(1, result[0].month)
        assertEquals(2022, result[0].year)
        assertEquals(100, result[0].price)
        assertEquals(10, result[0].discount)
        assertEquals("testCoverImageUrl1", result[0].coverImageUrl)
        assertEquals("testWhatsapp1", result[0].whatsapp)
        assertEquals("testDescription1", result[0].description)
    }
}