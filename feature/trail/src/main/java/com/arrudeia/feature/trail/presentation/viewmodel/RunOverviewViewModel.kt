package com.arrudeia.feature.trail.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.run.data.OfflineFirstRunRepository
import com.arrudeia.feature.trail.presentation.ui.run_overview.RunOverviewAction
import com.arrudeia.feature.trail.presentation.ui.run_overview.RunOverviewState
import com.arrudeia.feature.trail.presentation.ui.run_overview.mapper.toRunUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunOverviewViewModel @Inject constructor(
    private val runRepository: OfflineFirstRunRepository
) : ViewModel() {

    var state by mutableStateOf(RunOverviewState())
        private set

    init {


        runRepository.getRuns().onEach { runs ->
            val runsUi = runs.map { it.toRunUi() }
            state = state.copy(runs = runsUi)
        }.launchIn(viewModelScope)
    }

    fun onAction(action: RunOverviewAction) {
        when (action) {
            RunOverviewAction.OnStartClick -> Unit
            is RunOverviewAction.DeleteRun -> {
                viewModelScope.launch {
                    runRepository.deleteRun(action.runUi.id)
                }
            }

            else -> Unit
        }
    }
}