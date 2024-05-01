package com.arrudeia.feature.trip.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.result.Result
import com.arrudeia.core.test.ViewModelTest
import com.arrudeia.feature.trip.R
import com.arrudeia.feature.trip.domain.GetTravelByIdUseCase
import com.arrudeia.feature.trip.domain.entity.TravelUseCaseEntity
import com.arrudeia.feature.trip.presentation.map.mapTravelToUiModel
import com.arrudeia.feature.trip.presentation.navigation.TRIP_ID_ARG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class TripDetailViewModelTest : ViewModelTest(){

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TripDetailViewModel
    private val useCase = Mockito.mock(GetTravelByIdUseCase::class.java)
    private val savedStateHandle = SavedStateHandle()

    @Before
    fun setup() {
        savedStateHandle[TRIP_ID_ARG] = "1"
        viewModel = TripDetailViewModel(savedStateHandle, useCase)

    }

    @After
    fun `after each test`() {
        viewModel.viewModelScope.cancel()
    }

    @Test
    fun testFetchDataSuccess() = coTest {
        val trip = TravelUseCaseEntity(1, "Test", "Test", "Test", 1)
        `when`(useCase(1)).thenReturn(Result.Success(trip))

        viewModel.fetchData()

        Assert.assertTrue(viewModel.travelUiState.value is TripDetailViewModel.TripDetailUiState.Success)
        Assert.assertEquals(
            (viewModel.travelUiState.value as TripDetailViewModel.TripDetailUiState.Success).item,
            trip.mapTravelToUiModel()
        )
    }

    @Test
    fun testFetchDataError() = coTest {
        `when`(useCase.invoke(1)).thenReturn(Result.Error(R.string.erro_message_list_travels))

        viewModel.fetchData()

        Assert.assertTrue(viewModel.travelUiState.value is TripDetailViewModel.TripDetailUiState.Error)
        Assert.assertEquals(
            (viewModel.travelUiState.value as TripDetailViewModel.TripDetailUiState.Error).message,
            R.string.erro_message_list_travels
        )
    }

    @Test
    fun testFetchDataLoading() = coTest {
        `when`(useCase.invoke(1)).thenReturn(Result.Loading)

        viewModel.fetchData()

        Assert.assertTrue(viewModel.travelUiState.value is TripDetailViewModel.TripDetailUiState.Loading)
    }
}