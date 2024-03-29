package com.arrudeia.feature.stories

import androidx.lifecycle.SavedStateHandle
import com.arrudeia.core.domain.GetAllStoriesByIdUseCase
import com.arrudeia.core.entity.StoryUseCaseEntity
import com.arrudeia.feature.stories.navigation.storiesIdArg
import io.mockk.coEvery
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import io.mockk.mockk
import com.arrudeia.feature.stories.R.string.erro_message_list_travels
import com.arrudeia.feature.stories.navigation.map.mapStoriesToUiModel

@ExperimentalCoroutinesApi
class StoriesViewModelTest{

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var useCase: GetAllStoriesByIdUseCase
    private lateinit var viewModel: StoriesViewModel

    @Before
    fun setup() {
        useCase = mockk()
        savedStateHandle = SavedStateHandle(mapOf( storiesIdArg to "0" ))
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = StoriesViewModel(savedStateHandle, useCase)
    }

    @Test
    fun `test fetching stories success`() = runBlocking {
        // Given
        val mockStoriesId = "0"
        val mockStoriesList = listOf(StoryUseCaseEntity())
        coEvery { useCase.invoke(mockStoriesId.toLong()) } returns mockStoriesList

        // When
        viewModel.fetchStories()

        // Then
        val result = viewModel.uiState.value
        assertEquals(result, StoriesViewModel.StoriesUiState.Success(mockStoriesList.mapStoriesToUiModel()))
    }

    @Test
    fun `test fetching stories empty result`() = runBlocking {
        // Given
        val mockStoriesId = "0"
        coEvery { useCase.invoke(mockStoriesId.toLong()) } returns emptyList()

        // When
        viewModel.fetchStories()

        // Then
        val result = viewModel.uiState.value
        assertEquals(result, StoriesViewModel.StoriesUiState.Error(erro_message_list_travels))
    }

}