package com.arrudeia.feature.home.presentation.ui

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.arrudeia.core.common.R.string.events
import com.arrudeia.core.common.R.string.hotels
import com.arrudeia.core.designsystem.component.SimpleTabSwitch
import com.arrudeia.feature.home.presentation.navigation.param.EventDetailParam
import com.arrudeia.feature.home.presentation.navigation.param.HotelDetailParam
import com.arrudeia.feature.home.presentation.ui.events.EventHomeScreen
import com.arrudeia.feature.home.presentation.ui.hotels.HotelSearchScreen
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel

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


    SimpleTabSwitch(
        modifier = Modifier,
        //    selectedIndex = selectedTab,
        //    items = pages,
        tabsTitle = pages,
        screens = listOf(
            { HotelSearchScreen(viewModel, onShowSnackbar, onHotelDetailsClick) },
            { EventHomeScreen(viewModel, onShowSnackbar, onEventDetailsClick) }

        )
        //     onSelectionChange = {
        //         selectedTab = it
        //    }
    )

    /*    HorizontalPager(state = pagerState) { currentPage ->
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
        }*/

}


