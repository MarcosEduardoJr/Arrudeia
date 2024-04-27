package com.arrudeia.feature.home.presentation.viewmodel

import com.arrudeia.feature.home.presentation.model.HomeTravelUiModel

sealed interface TravelHomeResultUiState {
    data object Loading : TravelHomeResultUiState

    data object EmptyQuery : TravelHomeResultUiState

    data object LoadFailed : TravelHomeResultUiState

    data class Success(
        val travels: List<HomeTravelUiModel> = emptyList(),
    ) : TravelHomeResultUiState {
        fun isEmpty(): Boolean = travels.isEmpty()
    }

    data object HomeTravelNotReady : TravelHomeResultUiState
}
