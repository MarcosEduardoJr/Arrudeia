package com.arrudeia.feature.stories.stories


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(
     ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun Stories(
    numberOfPages: Int,
    indicatorModifier: Modifier = Modifier,
    spaceBetweenIndicator: Dp = 4.dp,
    indicatorBackgroundColor: Color = Color.LightGray,
    indicatorProgressColor: Color = Color.White,
    indicatorBackgroundGradientColors: List<Color> = emptyList(),
    slideDurationInSeconds: Long = 5,
    touchToPause: Boolean = true,
    hideIndicators: Boolean = false,
    onEveryStoryChange: ((Int) -> Unit)? = null,
    onComplete: () -> Unit,
    content: @Composable (Int) -> Unit
) {
    val pagerState = rememberPagerState{numberOfPages}

    val coroutineScope = rememberCoroutineScope()

    var pauseTimer by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        //Full screen content behind the indicator
        StoryImage(pagerState = pagerState, onTap = {
            if (touchToPause)
                pauseTimer = it
        }, content)

        //Indicator based on the number of items
        val modifier =
            if (hideIndicators) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            if (indicatorBackgroundGradientColors.isEmpty()) listOf(
                                Color.Black,
                                Color.Transparent
                            ) else indicatorBackgroundGradientColors
                        )
                    )
            }

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.padding(spaceBetweenIndicator))

            ListOfIndicators(
                numberOfPages,
                indicatorModifier,
                indicatorBackgroundColor,
                indicatorProgressColor,
                slideDurationInSeconds,
                pauseTimer,
                hideIndicators,
                coroutineScope,
                pagerState,
                spaceBetweenIndicator,
                onEveryStoryChange = onEveryStoryChange,
                onComplete = onComplete,
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RowScope.ListOfIndicators(
    numberOfPages: Int,
    modifier: Modifier,
    indicatorBackgroundColor: Color,
    indicatorProgressColor: Color,
    slideDurationInSeconds: Long,
    pauseTimer: Boolean,
    hideIndicators: Boolean,
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    spaceBetweenIndicator: Dp,
    onEveryStoryChange: ((Int) -> Unit)? = null,
    onComplete: () -> Unit,
) {
    var currentPage by remember {
        mutableIntStateOf(0)
    }

    for (index in 0 until numberOfPages) {
        LinearIndicator(
            modifier = modifier.weight(1f),
            index == currentPage,
            indicatorBackgroundColor,
            indicatorProgressColor,
            slideDurationInSeconds,
            pauseTimer,
            hideIndicators
        ) {
            coroutineScope.launch {

                currentPage++

                if (currentPage < numberOfPages) {
                    onEveryStoryChange?.invoke(currentPage)
                    pagerState.animateScrollToPage(currentPage)
                }

                if (currentPage == numberOfPages) {
                    onComplete()
                }
            }
        }

        Spacer(modifier = Modifier.padding(spaceBetweenIndicator))
    }
}