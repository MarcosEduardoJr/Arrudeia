

package com.arrudeia.feature.home

sealed interface TravelHomeResultUiState {
    data object Loading : TravelHomeResultUiState

    data object EmptyQuery : TravelHomeResultUiState

    data object LoadFailed : TravelHomeResultUiState

    data class Success(
        val travels: List<com.arrudeia.feature.home.model.HomeTravel> = emptyList(),
    ) : TravelHomeResultUiState {
        fun isEmpty(): Boolean = travels.isEmpty()
    }

    data object HomeTravelNotReady : TravelHomeResultUiState
}
