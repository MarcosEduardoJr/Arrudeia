package com.arrudeia.feature.home

import com.arrudeia.core.domain.GetAllArrudeiaTvUseCase
import com.arrudeia.core.domain.GetAllTravelHomeUseCase
import com.arrudeia.core.entity.ArrudeiaUseCaseEntity
import com.arrudeia.core.entity.TravelUseCaseEntity
import com.arrudeia.feature.home.R.string.erro_message_list_travels
import com.arrudeia.feature.home.map.mapArrTvToUiModel
import com.arrudeia.feature.home.map.mapTravelsToUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var travelUseCase: GetAllTravelHomeUseCase

    private lateinit var arrTvUseCase: GetAllArrudeiaTvUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        travelUseCase = Mockito.mock()
        arrTvUseCase = Mockito.mock()
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = HomeViewModel(travelUseCase, arrTvUseCase)
    }

    @Test
    fun `test fetching travel data success`() = runBlocking {
        // Given
        val mockTravelList = listOf(TravelUseCaseEntity())
        `when`(travelUseCase.invoke()).thenReturn(mockTravelList)
        // When
        viewModel.fetchDataTravels()
        // Then
        assertEquals(viewModel.travelUiState.value, TravelUiState.Success(mockTravelList.mapTravelsToUiModel()))
    }

    @Test
    fun `test fetching travel data empty result`() = runBlocking {
        // Given
        `when`(travelUseCase.invoke()).thenReturn(emptyList())

        // When
        viewModel.fetchDataTravels()

        // Then
        assertEquals(viewModel.travelUiState.value, TravelUiState.Error(erro_message_list_travels))
    }

    @Test
    fun `test fetching arrudeia TV data success`() = runBlocking {
        // Given
        val mockArrTvList = listOf(ArrudeiaUseCaseEntity())
        `when`(arrTvUseCase.invoke()).thenReturn(mockArrTvList)

        // When
        viewModel.fetchDataArrTv()

        // Then
        assertEquals(viewModel.arrTvUiState.value, ArrudeiaTvUiState.Success(mockArrTvList.mapArrTvToUiModel()))
    }

    @Test
    fun `test fetching arrudeia TV data empty result`() = runBlocking {
        // Given
        `when`(arrTvUseCase.invoke()).thenReturn(emptyList())

        // When
        viewModel.fetchDataArrTv()

        // Then
        assertEquals(viewModel.arrTvUiState.value, ArrudeiaTvUiState.Error(erro_message_list_travels))
    }
}
