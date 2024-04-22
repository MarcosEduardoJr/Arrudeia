package com.arrudeia.feature.stories.presentation.stories

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun LinearIndicator(
    modifier: Modifier,
    startProgress: Boolean = false,
    indicatorBackgroundColor: Color,
    indicatorProgressColor: Color,
    slideDurationInSeconds: Long,
    onPauseTimer: Boolean = false,
    hideIndicators: Boolean = false,
) {

    val delayInMillis = rememberSaveable {
        (slideDurationInSeconds * 1000) / 100
    }

    var progress by remember {
        mutableStateOf(0.00f)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )


    if (startProgress) {
        LaunchedEffect(key1 = onPauseTimer) {
            while (progress < 1f && isActive && onPauseTimer.not()) {
                progress += 0.01f
                delay(delayInMillis)
            }

            if (onPauseTimer.not()) {
                delay(200)
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