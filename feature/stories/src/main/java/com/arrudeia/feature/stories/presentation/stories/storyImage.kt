package com.arrudeia.feature.stories.presentation.stories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalComposeUiApi
@Composable
fun storyImage(
    pagerState: PagerState,
    content: @Composable (Int) -> Unit
) {
    HorizontalPager(state = pagerState, modifier = Modifier ) {
        content(it)
    }
}