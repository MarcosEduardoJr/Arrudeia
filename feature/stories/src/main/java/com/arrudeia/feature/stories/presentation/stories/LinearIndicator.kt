package com.arrudeia.feature.stories.presentation.stories

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Suppress("LongParameterList")
@Composable
fun linearIndicator(
    modifier: Modifier,
    startProgress: Boolean = false,
    indicatorBackgroundColor: Color,
    indicatorProgressColor: Color,
    slideDurationInSeconds: Long,
    onPauseTimer: Boolean = false,
) {
    val hideIndicators: Boolean = false
    val delayInMillis = rememberSaveable {
        (slideDurationInSeconds * SLIDE_TIME_DURATION_SECOND) / SLIDE_TIME_DURATION_SECOND_CONVERTER
    }

    var progress by remember {
        mutableStateOf(PROGRESS_START)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )


    if (startProgress) {
        LaunchedEffect(key1 = onPauseTimer) {
            while (progress < 1f && isActive && onPauseTimer.not()) {
                progress += PROGRESS_SLICE
                delay(delayInMillis)
            }

            if (onPauseTimer.not()) {
                delay(SHORT_DELAY_PROGRESS)
            }
        }
    }

    if (hideIndicators.not()) {
        LinearProgressIndicator(
            trackColor = indicatorBackgroundColor,
            color = indicatorProgressColor,
            modifier = modifier
                .padding(top = 12.dp, bottom = 12.dp)
                .clip(RoundedCornerShape(12.dp)),
            progress = animatedProgress
        )
    }
}


const val PROGRESS_START = 0.00f
const val PROGRESS_SLICE = 0.01f
const val SLIDE_TIME_DURATION_SECOND = 1000
const val SLIDE_TIME_DURATION_SECOND_CONVERTER = 100
const val SHORT_DELAY_PROGRESS: Long = 200