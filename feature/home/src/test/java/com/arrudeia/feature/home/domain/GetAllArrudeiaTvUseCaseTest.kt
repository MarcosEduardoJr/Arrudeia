package com.arrudeia.feature.home.domain

import com.arrudeia.feature.home.data.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.feature.home.data.entity.ArrudeiaTvRepositoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class GetAllArrudeiaTvUseCaseTest {

    private val repository = mock(DefaultHomeTravelsRepositoryImpl::class.java)
    private val useCase = GetAllArrudeiaTvUseCase(repository)

    @Test
    fun `invoke calls repository`() = runBlocking {
        val arrudeiaTvList = listOf(
            ArrudeiaTvRepositoryEntity(
                id = 0,
                image_url = "testImageUrl1"
            ),
            ArrudeiaTvRepositoryEntity(
                id = 1,
                image_url = "testImageUrl2"
            )
        )

        `when`(repository.getAllArrudeiaTv()).thenReturn(arrudeiaTvList)

        val result = useCase()

        verify(repository).getAllArrudeiaTv()
        assertEquals(2, result.size)
        assertEquals(0, result[0].id)
        assertEquals("testImageUrl1", result[0].imageUrl)
        assertEquals(1, result[1].id)
        assertEquals("testImageUrl2", result[1].imageUrl)
    }
}