package com.arrudeia.feature.stories.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.stories.data.StoriesRepositoryImpl
import com.arrudeia.feature.stories.data.entity.StoryRepositoryEntity
import com.arrudeia.feature.stories.domain.entity.StoryUseCaseEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetAllStoriesByIdUseCaseTest {

    private val repository: StoriesRepositoryImpl = mockk()
    private val useCase = GetAllStoriesByIdUseCase(repository)

    @Test
    fun `invoke returns expected result`() = runBlocking {
        val id = 1L
        val storyEntity = StoryRepositoryEntity(image_url = "url")
        val expected = Result.Success(listOf(StoryUseCaseEntity(image = "url")))

        coEvery { repository.getStoriesById(id) } returns Result.Success(listOf(storyEntity))

        val result = useCase.invoke(id)

        Assert.assertEquals(expected, result)
    }
}