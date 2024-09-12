package com.arrudeia.feature.home.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arrudeia.core.common.R.string.events
import com.arrudeia.core.common.R.string.hotels
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.component.TextSwitch
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import com.arrudeia.feature.home.presentation.navigation.param.EventDetailParam
import com.arrudeia.feature.home.presentation.navigation.param.HotelDetailParam
import com.arrudeia.feature.home.presentation.ui.events.EventHomeScreen
import com.arrudeia.feature.home.presentation.ui.hotels.HotelSearchScreen

@Composable
fun PagerHome(
    viewModel: HomeViewModel,
    searchChange: (String) -> Unit,
    onHotelDetailsClick: (HotelDetailParam) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onEventDetailsClick: (EventDetailParam) -> Unit,
) {
    val pages = listOf(
        stringResource(id = hotels),
        stringResource(id = events),
    )
    var pagerState = rememberPagerState(initialPage = 0) { pages.size }
    var selectedTab by rememberSaveable { mutableIntStateOf(pagerState.currentPage) }


    LaunchedEffect(selectedTab) {
        pagerState.scrollToPage(selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTab = pagerState.currentPage
        searchChange("")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = background_grey_F7F7F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = background_grey_F7F7F9)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(6.dp))

            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
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
            Spacer(modifier = Modifier.size(10.dp))

            HorizontalPager(state = pagerState) { currentPage ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start
                ) {
                    when (currentPage) {
                        0 -> {
                            HotelSearchScreen(viewModel, onShowSnackbar, onHotelDetailsClick)
                        }

                        1 -> {
                            EventHomeScreen(viewModel, onShowSnackbar, onEventDetailsClick)
                        }
                    }
                }
            }
        }
    }
}


