package com.arrudeia.core.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImagePagerWithDots(images: List<String>, maxDots: Int = 5, modifier: Modifier, modifierDots : Modifier = Modifier,cardCornerShape : Dp = 12.dp ) {
    var pagerState = rememberPagerState(initialPage = 0) { images.size }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(cardCornerShape)
    ) {
        Box() {
            HorizontalPager(
                state = pagerState,
            ) { page ->

                GlideImage(
                    model = images[page].orEmpty(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }


            DotsIndicator(
                totalDots = images.size,
                selectedIndex = pagerState.currentPage,
                maxDots = maxDots,
                modifier = modifierDots.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun DotsIndicator(totalDots: Int, selectedIndex: Int, maxDots: Int, modifier: Modifier) {
    val visibleDots = minOf(totalDots, maxDots)
    val startIndex = maxOf(0, selectedIndex - visibleDots / 2)
    val endIndex = minOf(totalDots, startIndex + visibleDots)

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        for (i in startIndex until endIndex) {
            val isSelected = i == selectedIndex
            val dotSize by animateFloatAsState(
                targetValue = if (isSelected) 18f else 16f,
                animationSpec = tween(durationMillis = 300)
            )
            val alphaColor = if (isSelected) 1f else 0.7f
            val dotColor by animateColorAsState(
                targetValue = colorResource(id = R.color.colorPrimary).copy(alpha = alphaColor),
                animationSpec = tween(durationMillis = 300)
            )

            Box(
                modifier = Modifier
                    .size(dotSize.dp)
                    .padding(4.dp)
                    .background(color = dotColor, shape = CircleShape)
            )
        }
    }
}