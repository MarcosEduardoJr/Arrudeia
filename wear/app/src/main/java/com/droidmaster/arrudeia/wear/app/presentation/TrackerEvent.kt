package com.arrudeia.wear.app.presentation

import com.arrudeia.core.designsystem.component.util.UiText


sealed interface TrackerEvent {
    data object RunFinished: TrackerEvent
    data class Error(val message: UiText): TrackerEvent
}