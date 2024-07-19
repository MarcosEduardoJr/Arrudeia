package com.arrudeia.feature.arrudeia.domain

import com.arrudeia.core.result.Result
import com.arrudeia.core.places.data.ArrudeiaPlaceRepositoryImpl
import com.arrudeia.core.places.data.entity.ArrudeiaPlaceRepositoryEntity
import com.arrudeia.core.places.data.entity.AvailableRepositoryEntity
import com.arrudeia.core.places.domain.GetAllArrudeiaPlacesUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class GetAllArrudeiaPlacesUseCaseTest {

    private val repository = mock(ArrudeiaPlaceRepositoryImpl::class.java)
    private val useCase = GetAllArrudeiaPlacesUseCase(repository)

    @Test
    fun `invoke calls repository`() = runBlocking {
        val arrudeiaPlaceList = listOf(
            ArrudeiaPlaceRepositoryEntity(
                available = listOf(
                    AvailableRepositoryEntity("0", name = "name", placeId = "0")
                ),
                categoryName = "testCategoryName1",
                description = "testDescription1",
                image = "testImage1",
                latitude = 1.0,
                longitude = 1.0,
                name = "testName1",
                phone = "testPhone1",
                priceLevel = 1,
                rating = 1,
                socialNetwork = "testSocialNetwork1",
                subCategoryName = "testSubCategoryName1",
                uuid = "testUuid1"
            ),
            ArrudeiaPlaceRepositoryEntity(
                available = listOf(
                    AvailableRepositoryEntity("1", name = "name", placeId = "1")
                ),
                categoryName = "testCategoryName1",
                description = "testDescription1",
                image = "testImage1",
                latitude = 1.0,
                longitude = 1.0,
                name = "testName1",
                phone = "testPhone1",
                priceLevel = 1,
                rating = 1,
                socialNetwork = "testSocialNetwork1",
                subCategoryName = "testSubCategoryName1",
                uuid = "testUuid1"
            ),
        )

        `when`(repository.getArrudeiaPlaces("")).thenReturn(Result.Success(arrudeiaPlaceList))

        val result = useCase("")

        verify(repository).getArrudeiaPlaces("")
        assertTrue(result is Result.Success)
        result as Result.Success
        assertEquals(2, result.data?.size)
        assertEquals("testUuid1", result.data?.get(0)?.uuid)
        assertEquals("testName1", result.data?.get(0)?.name)
        assertEquals("testCategoryName1", result.data?.get(0)?.categoryName)
        assertEquals("testDescription1", result.data?.get(0)?.description)
        assertEquals("testImage1", result.data?.get(0)?.image)
        assertEquals(LatLng(1.0, 1.0), result.data?.get(0)?.location)
        assertEquals("testPhone1", result.data?.get(0)?.phone)
        assertEquals(1, result.data?.get(0)?.priceLevel)
        assertEquals(1, result.data?.get(0)?.rating)
        assertEquals("testSocialNetwork1", result.data?.get(0)?.socialNetwork)
        assertEquals("testSubCategoryName1", result.data?.get(0)?.subCategoryName)
    }
}