package com.arrudeia.feature.stories.presentation.stories


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun stories(
    numberOfPages: Int,
    onEveryStoryChange: ((Int) -> Unit)? = null,
    currentPage: Int,
    content: @Composable (Int) -> Unit,
) {
    val pagerState = rememberPagerState { numberOfPages }
    val spaceBetweenIndicator: Dp = 4.dp
    val slideDurationInSeconds: Long = 0
    val hideIndicators: Boolean = false
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
            onEveryStoryChange?.invoke(currentPage)
            pagerState.animateScrollToPage(currentPage)
        }

    Box(modifier = Modifier.fillMaxSize()) {
        //Full screen content behind the indicator
        storyImage(pagerState = pagerState, content)

        //Indicator based on the number of items
        val modifier =
            if (hideIndicators) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Black,
                                Color.Transparent

                            )
                        )
                    )
            }

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.padding(spaceBetweenIndicator))

            listOfIndicators(
                numberOfPages = numberOfPages,
                slideDurationInSeconds = slideDurationInSeconds,
                pauseTimer = false,
                spaceBetweenIndicator = spaceBetweenIndicator,
                currentPage = currentPage,
            )
        }
    }

}

@Composable
private fun RowScope.listOfIndicators(
    numberOfPages: Int,
    slideDurationInSeconds: Long,
    pauseTimer: Boolean,
    spaceBetweenIndicator: Dp,
    currentPage: Int,
) {


    val indicatorBackgroundColor: Color = Color.LightGray
    val indicatorProgressColor: Color = Color.White
    for (index in 0 until numberOfPages) {
        linearIndicator(
            modifier = Modifier.weight(1f),
            index == currentPage,
            indicatorBackgroundColor,
            indicatorProgressColor,
            slideDurationInSeconds,
            pauseTimer,
        )
        Spacer(modifier = Modifier.padding(spaceBetweenIndicator))
    }
}