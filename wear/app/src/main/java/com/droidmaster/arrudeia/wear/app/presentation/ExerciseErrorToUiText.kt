package com.droidmaster.arrudeia.wear.app.presentation

import com.arrudeia.core.designsystem.component.util.UiText
import com.droidmaster.arrudeia.wear.app.R
import com.arrudeia.core.run.domain.wear.ExerciseError

fun ExerciseError.toUiText(): UiText? {
    return when(this) {
        ExerciseError.ONGOING_OWN_EXERCISE,
        ExerciseError.ONGOING_OTHER_EXERCISE -> {
            UiText.StringResource(R.string.error_ongoing_exercise)
        }
        ExerciseError.EXERCISE_ALREADY_ENDED -> {
            UiText.StringResource(R.string.error_exercise_already_ended)
        }
        ExerciseError.UNKNOWN -> UiText.StringResource( R.string.error_unknown)
        ExerciseError.TRACKING_NOT_SUPPORTED -> null
    }
}