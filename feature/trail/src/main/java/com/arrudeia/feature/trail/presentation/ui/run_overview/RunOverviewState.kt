package com.arrudeia.feature.trail.presentation.ui.run_overview

import com.arrudeia.feature.trail.presentation.ui.run_overview.model.RunUi

data class RunOverviewState(
    val runs: List<RunUi> = emptyList()
)
