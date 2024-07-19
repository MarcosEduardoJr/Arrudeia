package com.arrudeia.feature.trail.presentation.ui.run_overview

import com.arrudeia.feature.trail.presentation.ui.run_overview.model.RunUi

sealed interface RunOverviewAction {
    data object OnStartClick: RunOverviewAction
    data object OnLogoutClick: RunOverviewAction
    data object OnAnalyticsClick: RunOverviewAction
    data class DeleteRun(val runUi: RunUi): RunOverviewAction
}