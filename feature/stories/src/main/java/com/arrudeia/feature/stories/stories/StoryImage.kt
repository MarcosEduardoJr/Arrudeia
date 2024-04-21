package com.arrudeia.feature.stories.stories

import android.view.MotionEvent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalComposeUiApi
@Composable
fun StoryImage(
    pagerState: PagerState,
    onTap: (Boolean) -> Unit,
    content: @Composable (Int) -> Unit
) {
    HorizontalPager(state = pagerState, modifier = Modifier ) {
        content(it)
    }
}