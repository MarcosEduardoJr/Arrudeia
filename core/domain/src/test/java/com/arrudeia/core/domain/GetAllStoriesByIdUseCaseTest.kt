package com.arrudeia.core.domain

import com.arrudeia.feature.stories.data.StoriesRepositoryImpl
import com.arrudeia.feature.stories.data.entity.StoryRepositoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertTrue


class GetAllStoriesByIdUseCaseTest {

    private lateinit var repository: com.arrudeia.feature.stories.data.StoriesRepositoryImpl
    private lateinit var useCase: com.arrudeia.feature.stories.domain.GetAllStoriesByIdUseCase

    @Before
    fun setup() {
        repository = Mockito.mock()
        useCase = com.arrudeia.feature.stories.domain.GetAllStoriesByIdUseCase(repository)
    }

    @Test
    fun `invoke use case is not empty`() = runBlocking {
        val listResult = listOf(com.arrudeia.feature.stories.data.entity.StoryRepositoryEntity())
        Mockito.`when`(repository.getStoriesById(0)).thenReturn(listResult)
        assertTrue(useCase.invoke(0).isNotEmpty())
    }
}