package com.arrudeia.feature.trip

import androidx.lifecycle.SavedStateHandle
import com.arrudeia.feature.trip.domain.GetTravelByIdUseCase
import com.arrudeia.core.entity.TravelUseCaseEntity
import com.arrudeia.feature.trip.R.string.erro_message_list_travels
import com.arrudeia.feature.trip.presentation.map.mapTravelToUiModel
import com.arrudeia.feature.trip.navigation.tripIdArg
import com.arrudeia.feature.trip.presentation.viewmodel.TripDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
    class TripDetailViewModelTest {
        private lateinit var savedStateHandle: SavedStateHandle
        private lateinit var travelUseCase: GetTravelByIdUseCase
        private lateinit var viewModel: TripDetailViewModel

        @Before
        fun setup() {
            savedStateHandle = SavedStateHandle(mapOf( tripIdArg to "0" ))
            travelUseCase = mockk(relaxed = true)
            Dispatchers.setMain(Dispatchers.Unconfined)
            viewModel = TripDetailViewModel(savedStateHandle, travelUseCase)
        }

        @Test
        fun `test fetching trip data success`() = runBlocking {
            // Given
            val mockTripId = "0"
            val mockTrip = TravelUseCaseEntity()

            coEvery { travelUseCase.invoke(mockTripId.toLong()) } returns mockTrip

            // When
            viewModel.fetchData()

            // Then
            val result = viewModel.travelUiState.value
            assertEquals(result, TripDetailViewModel.TripDetailUiState.Success(mockTrip.mapTravelToUiModel()))
        }

        @Test
        fun `test fetching trip data null result`() = runBlocking {
            // Given
            val mockTripId = "0"
            coEvery { travelUseCase.invoke(mockTripId.toLong()) } returns null

            // When
            viewModel.fetchData()

            // Then
            val result = viewModel.travelUiState.value
            assertEquals(result, TripDetailViewModel.TripDetailUiState.Error(erro_message_list_travels))
        }

    }