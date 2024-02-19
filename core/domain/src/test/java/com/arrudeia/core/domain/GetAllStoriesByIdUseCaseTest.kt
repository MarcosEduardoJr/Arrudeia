package com.arrudeia.core.domain

import com.arrudeia.core.data.network.StoriesRepositoryImpl
import com.arrudeia.core.data.repository.StoryRepositoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertTrue


class GetAllStoriesByIdUseCaseTest {

    private lateinit var repository: StoriesRepositoryImpl
    private lateinit var useCase: GetAllStoriesByIdUseCase

    @Before
    fun setup() {
        repository = Mockito.mock()
        useCase = GetAllStoriesByIdUseCase(repository)
    }

    @Test
    fun `invoke use case is not empty`() = runBlocking {
        val listResult = listOf(StoryRepositoryEntity())
        Mockito.`when`(repository.getStoriesById(0)).thenReturn(listResult)
        assertTrue(useCase.invoke(0).isNotEmpty())
    }
}