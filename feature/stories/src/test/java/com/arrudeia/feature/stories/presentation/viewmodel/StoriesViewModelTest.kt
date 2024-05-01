package com.arrudeia.feature.stories.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.arrudeia.core.result.Result
import com.arrudeia.core.test.ViewModelTest
import com.arrudeia.feature.stories.R
import com.arrudeia.feature.stories.domain.GetAllStoriesByIdUseCase
import com.arrudeia.feature.stories.domain.entity.StoryUseCaseEntity
import com.arrudeia.feature.stories.presentation.navigation.STORIES_ID_ARG
import com.arrudeia.feature.stories.presentation.navigation.map.mapStoriesToUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class StoriesViewModelTest  : ViewModelTest(){

    private lateinit var viewModel: StoriesViewModel
    private val useCase = Mockito.mock(GetAllStoriesByIdUseCase::class.java)
    private val savedStateHandle = SavedStateHandle()


    @Before
    fun setup() {
        savedStateHandle[STORIES_ID_ARG] = "1"

        viewModel = StoriesViewModel(savedStateHandle, useCase)
    }

    @Test
    fun testFetchStoriesSuccess() = coTest {
        var stories :List<StoryUseCaseEntity>?
        stories = listOf(StoryUseCaseEntity( "Test"))
        `when`(useCase.invoke(1)).thenReturn(Result.Success(stories))

        viewModel.fetchStories()

        Assert.assertTrue(viewModel.uiState.value is StoriesViewModel.StoriesUiState.Success)
        Assert.assertEquals((viewModel.uiState.value as StoriesViewModel.StoriesUiState.Success).list, stories.mapStoriesToUiModel())
    }

    @Test
    fun testFetchStoriesError() = coTest {
        `when`(useCase.invoke(1)).thenReturn(Result.Error(R.string.erro_message_stories))

        viewModel.fetchStories()

        Assert.assertTrue(viewModel.uiState.value is StoriesViewModel.StoriesUiState.Error)
        Assert.assertEquals((viewModel.uiState.value as StoriesViewModel.StoriesUiState.Error).message, R.string.erro_message_stories)
    }

    @Test
    fun testFetchStoriesLoading() = coTest {
        `when`(useCase.invoke(1)).thenReturn(Result.Loading)

        viewModel.fetchStories()

        Assert.assertTrue(viewModel.uiState.value is StoriesViewModel.StoriesUiState.Loading)
    }
}