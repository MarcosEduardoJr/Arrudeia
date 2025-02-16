package com.arrudeia.feature.home.presentation.ui.hotels.detail

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.component.ImagePagerWithDots
import com.arrudeia.core.designsystem.component.TextSwitch
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.home.R.string.infomations
import com.arrudeia.feature.home.R.string.prices
import com.arrudeia.feature.home.data.entity.hotel.HotelDetailResponse
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@Composable
internal fun HotelDetailRoute(
    onBackClick: () -> Unit,
    query: String,
    checkInDate: String,
    checkOutDate: String,
    adults: Int,
    children: Int,
    childrenAges: String,
    propertyToken: String,
    viewModel: HomeViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
    amenities: List<String> = listOf<String>(),
) {
    val state by viewModel.hotelDetailState.collectAsState()
    var message by remember { mutableStateOf("") }

    LaunchedEffect(message) {
        if (message.isNotEmpty())
            onShowSnackbar(message, "")
    }

    viewModel.fetchHotelDetail(
        query,
        checkInDate,
        checkOutDate,
        adults,
        children,
        childrenAges,
        propertyToken
    )

    when (state) {
        is HomeViewModel.HotelDetailState.Loading -> {
            Box(
                Modifier
                    .fillMaxSize()
            ) {
                ArrudeiaLoadingWheel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .height(50.dp),
                )
            }
        }

        is HomeViewModel.HotelDetailState.Success -> {

            val result = (state as HomeViewModel.HotelDetailState.Success).data
            HotelDetail(
                result,
                onBackClick,
                amenities
            )
        }

        is HomeViewModel.HotelDetailState.Error -> {
            message = (state as HomeViewModel.HotelDetailState.Error).message

        }
    }


}


@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("DesignSystem")
@Composable
internal fun HotelDetail(
    item: HotelDetailResponse,
    onBackClick: () -> Unit,
    amenities: List<String>
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
                        item.images?.map { it.original_image.orEmpty() }?.toList().orEmpty(),
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth(),
                        maxDots = 3,
                        modifierDots = Modifier.padding(bottom = 40.dp),
                        cardCornerShape = 0.dp
                    )
                    // HotelDetailContent(item = item, amenities)
                    SwitchTabHomeDetail(item, amenities)
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
fun SwitchTabHomeDetail(
    item: HotelDetailResponse,
    amenities: List<String>
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
                            HotelOfferOptionItem(item.featured_prices.orEmpty())
                        }

                        1 -> {
                            HotelDetailContent(item = item, amenities)
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
