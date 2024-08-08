package com.arrudeia.feature.trail.presentation.ui.active_run.maps

import androidx.compose.ui.graphics.Color
import com.arrudeia.core.run.domain.location.Location

data class PolylineUi(
    val location1: Location,
    val location2: Location,
    val color: Color
)
