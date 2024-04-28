package com.arrudeia.feature.onboarding.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.arrudeia.core.result.Result
import com.arrudeia.feature.onboarding.domain.GetCurrentUserDataStoreUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class OnboardingViewModelTest : ViewModelTest() {
    private lateinit var useCase: GetCurrentUserDataStoreUseCase
    private lateinit var viewModel: OnboardingViewModel


    @Before
    fun setup() {
        useCase = mockk()
        viewModel = OnboardingViewModel(useCase)

    }

    @After
    fun `after each test`() {
        viewModel.viewModelScope.cancel()
    }

    @Test
    fun `getCurrentUser returns Success when user is saved`() = coTest {
        coEvery { useCase() } returns Result.Success(true)

        viewModel.getCurrentUser()

        Assert.assertTrue(viewModel.currentUserUiState.value is OnboardingViewModel.CurrentUserUiState.Success)
    }

    @Test
    fun `getCurrentUser returns Error when user is not saved`() = coTest {
        coEvery { useCase() } returns Result.Error(null)

        viewModel.getCurrentUser()

        Assert.assertTrue(viewModel.currentUserUiState.value is OnboardingViewModel.CurrentUserUiState.Error)
    }
}