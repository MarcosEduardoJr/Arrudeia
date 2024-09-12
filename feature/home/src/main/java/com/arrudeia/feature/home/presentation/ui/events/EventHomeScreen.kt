package com.arrudeia.feature.home.presentation.ui.events

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.feature.home.data.entity.events.GoogleEventsResult
import com.arrudeia.feature.home.presentation.navigation.param.EventDetailParam
import com.arrudeia.feature.home.presentation.ui.CurrentCity
import com.arrudeia.feature.home.presentation.ui.search
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import com.google.gson.Gson

@Composable
fun EventHomeScreen(
    viewModel: HomeViewModel,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onEventDetailsClick: (EventDetailParam) -> Unit,
) {
    val state by viewModel.eventSearchState.collectAsState()
    var list by rememberSaveable { mutableStateOf(mutableListOf<GoogleEventsResult>()) }
    var message by remember { mutableStateOf("") }
    var query by rememberSaveable { mutableStateOf<String?>(null) }
    var searchNow by remember { mutableStateOf(false) }

    if (query == null)
        CurrentCity({
            query = it
            searchNow = true
        }, viewModel)
    search(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentHeight(Alignment.CenterVertically)
            .clip(CircleShape),
        query.orEmpty(),
        onSearchTravelChange = { query = it },
    )

    ArrudeiaButtonColor(
        onClick = {
            searchNow = true

        },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 2.dp)
            .fillMaxWidth(),
        colorButton = colorResource(R.color.colorPrimary),
    ) {
        Text(
            text = stringResource(id = com.arrudeia.core.common.R.string.search),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
        )
    }
    if (searchNow) {

        viewModel.searchEvents(
            query.orEmpty(),
        )
        searchNow = false
    }
    LaunchedEffect(message) {
        if (message.isNotEmpty())
            onShowSnackbar(message, "")
    }

    when (state) {
        is HomeViewModel.GoogleEventSearchState.Loading -> {
            list.clear()
            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                ArrudeiaLoadingWheel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .height(50.dp),
                )
            }
        }

        is HomeViewModel.GoogleEventSearchState.Success -> {
            searchNow = false
            val result = (state as HomeViewModel.GoogleEventSearchState.Success).data
            result?.events_results?.let {
                list.clear() // so dar clear se for search now se for relamente mantem
                list.addAll(it.filter { it.image?.isNotEmpty() == true })
            }
        }

        is HomeViewModel.GoogleEventSearchState.Error -> {
            message = (state as HomeViewModel.GoogleEventSearchState.Error).message

        }
    }
    /*
        hotelList.addAll(
            listOf(
                Property(
                    name = "Yana Villas Kemenuh",
                    overall_rating = 4.5,
                    reviews = 100,
                    total_rate = TotalRate(lowest = "R$ 100"),
                    images = listOf(
                        HotelItemImage(original_image = "https://q-xx.bstatic.com/xdata/images/hotel/max1440x1080/223247563.jpg?k=17e4b19b4710d224007544d313b2e012e33475d53877bdea8cf036d8f6a60c1c&o="),
                        HotelItemImage(original_image = "https://q-xx.bstatic.com/xdata/images/hotel/max1440x1080/223247563.jpg?k=17e4b19b4710d224007544d313b2e012e33475d53877bdea8cf036d8f6a60c1c&o="),
                        HotelItemImage(original_image = "https://q-xx.bstatic.com/xdata/images/hotel/max1440x1080/223247563.jpg?k=17e4b19b4710d224007544d313b2e012e33475d53877bdea8cf036d8f6a60c1c&o="),
                        HotelItemImage(original_image = "https://q-xx.bstatic.com/xdata/images/hotel/max1440x1080/223247563.jpg?k=17e4b19b4710d224007544d313b2e012e33475d53877bdea8cf036d8f6a60c1c&o="),
                        HotelItemImage(original_image = "https://q-xx.bstatic.com/xdata/images/hotel/max1440x1080/223247563.jpg?k=17e4b19b4710d224007544d313b2e012e33475d53877bdea8cf036d8f6a60c1c&o="),
                        HotelItemImage(original_image = "https://q-xx.bstatic.com/xdata/images/hotel/max1440x1080/223247563.jpg?k=17e4b19b4710d224007544d313b2e012e33475d53877bdea8cf036d8f6a60c1c&o="),
                    )
                )
            )
        )*/
    if (list.isNotEmpty())
        EventItem(list) {
            onEventDetailsClick(it)
        }
}
