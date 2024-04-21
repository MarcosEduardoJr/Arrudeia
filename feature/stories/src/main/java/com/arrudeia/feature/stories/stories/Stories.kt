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
    slideDurationInSeconds: Long = 0,
    touchToPause: Boolean = true,
    hideIndicators: Boolean = false,
    onEveryStoryChange: ((Int) -> Unit)? = null,
    onComplete: () -> Unit,
    currentPage : Int,
    content: @Composable (Int) -> Unit,
) {
    val pagerState = rememberPagerState { numberOfPages }

    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
    onEveryStoryChange?.invoke(currentPage)
    pagerState.animateScrollToPage(currentPage)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        //Full screen content behind the indicator
        StoryImage(pagerState = pagerState, onTap = {}, content)

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
                false,
                hideIndicators,
                coroutineScope,
                pagerState,
                spaceBetweenIndicator,
                currentPage = currentPage,
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
    currentPage: Int,
    onComplete: () -> Unit,
) {

    for (index in 0 until numberOfPages) {
        LinearIndicator(
            modifier = modifier.weight(1f),
            index == currentPage,
            indicatorBackgroundColor,
            indicatorProgressColor,
            slideDurationInSeconds,
            pauseTimer,
        )
        Spacer(modifier = Modifier.padding(spaceBetweenIndicator))
    }
}