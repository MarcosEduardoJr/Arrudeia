

package com.arrudeia.core.domain

import com.arrudeia.core.model.data.HomeTravel

sealed interface TravelHomeResultUiState {
    data object Loading : TravelHomeResultUiState

    data object EmptyQuery : TravelHomeResultUiState

    data object LoadFailed : TravelHomeResultUiState

    data class Success(
        val travels: List<HomeTravel> = emptyList(),
    ) : TravelHomeResultUiState {
        fun isEmpty(): Boolean = travels.isEmpty()
    }

    data object HomeTravelNotReady : TravelHomeResultUiState
}
