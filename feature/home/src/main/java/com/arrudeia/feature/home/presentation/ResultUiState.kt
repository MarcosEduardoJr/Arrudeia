package com.arrudeia.feature.home.presentation

import com.arrudeia.feature.home.data.entity.TravelRepositoryEntity


sealed interface ResultUiState {
    data object Loading : ResultUiState
    data object LoadFailed : ResultUiState

    data class Success(
        val list: List<TravelRepositoryEntity> = emptyList()
    ) : ResultUiState {
        fun isEmpty(): Boolean = list.isEmpty()
    }
}
