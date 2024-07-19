package com.arrudeia.feature.trail.presentation.ui.active_run

import com.arrudeia.core.designsystem.component.util.UiText


sealed interface ActiveRunEvent {
    data class Error(val error: UiText): ActiveRunEvent
    data object RunSaved: ActiveRunEvent
}