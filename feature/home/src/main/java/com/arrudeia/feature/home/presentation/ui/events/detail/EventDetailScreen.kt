package com.arrudeia.feature.home.presentation.ui.events.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.component.ImagePagerWithDots
import com.arrudeia.core.designsystem.component.TextSwitch
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.arrudeia.feature.home.R.string.infomations
import com.arrudeia.feature.home.R.string.prices
import com.arrudeia.feature.home.data.entity.events.GoogleEventsResult

@Composable
internal fun EventDetailRoute(
    onBackClick: () -> Unit,
    event: GoogleEventsResult
) {
    EventDetail(
        event,
        onBackClick,
    )
}


@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("DesignSystem")
@Composable
internal fun EventDetail(
    item: GoogleEventsResult,
    onBackClick: () -> Unit
) {
    ArrudeiaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorResource(id = background_grey_F7F7F9)
            ) {
                Box(
                    modifier = with(Modifier) {
                        fillMaxSize()
                    })
                {

                    ImagePagerWithDots(
                        listOf(item.image.orEmpty()),
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth(),
                        maxDots = 0,
                        modifierDots = Modifier.padding(bottom = 40.dp),
                        cardCornerShape = 0.dp
                    )
                    SwitchTabEventDetail(item)
                }
            }
            buttonBottom(
                onBackClick, Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun SwitchTabEventDetail(
    item: GoogleEventsResult
) {
    val pages = listOf(
        stringResource(id = prices),
        stringResource(id = infomations),
    )
    var pagerState = rememberPagerState(initialPage = 0) { pages.size }
    var selectedTab by rememberSaveable { mutableIntStateOf(pagerState.currentPage) }


    LaunchedEffect(selectedTab) {
        pagerState.scrollToPage(selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTab = pagerState.currentPage
    }

    Box(
        modifier = Modifier
            .padding(top = 260.dp)
            .fillMaxSize()
            .clipToBounds()
            .clip(RoundedCornerShape(12.dp))
            .background(color = colorResource(id = background_grey_F7F7F9)),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = background_grey_F7F7F9)),
            horizontalAlignment = Alignment.Start
        ) {

            HotelHeaderContentDescription(item)

            Box(modifier = Modifier.padding(16.dp)) {
                TextSwitch(
                    modifier = Modifier
                        .height(56.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color.White)
                        .padding(8.dp),
                    selectedIndex = selectedTab,
                    items = pages,
                    onSelectionChange = {
                        selectedTab = it
                    }
                )
            }

            HorizontalPager(state = pagerState) { currentPage ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start
                ) {
                    when (currentPage) {
                        0 -> {

                            EventTicketsItem(item.ticket_info)
                        }

                        1 -> {
                            EventDetailContent(item = item)
                        }
                    }
                }
            }
        }

    }
}


@Composable
private fun buttonBottom(onBackClick: () -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
    ) {

        CircularIconButton(
            onClick = {
                onBackClick()
            },
            icon = Icons.Rounded.ArrowBack,
            backgroundColor = colorResource(id = background_grey_F7F7F9),
            iconSize = 50.dp,
            modifier = Modifier
        )
    }
}
