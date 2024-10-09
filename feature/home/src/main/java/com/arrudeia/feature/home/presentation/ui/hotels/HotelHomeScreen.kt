package com.arrudeia.feature.home.presentation.ui.hotels

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import com.arrudeia.core.designsystem.component.ArrudeiaDateRangeHotel
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.ArrudeiaPeopleCount
import com.arrudeia.feature.home.data.entity.hotel.Property
import com.arrudeia.feature.home.presentation.navigation.param.HotelDetailParam
import com.arrudeia.feature.home.presentation.ui.CurrentCity
import com.arrudeia.feature.home.presentation.ui.HotelItem
import com.arrudeia.feature.home.presentation.ui.search
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel

@Composable
fun HotelSearchScreen(
    viewModel: HomeViewModel,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onHotelDetailsClick: (HotelDetailParam) -> Unit,
) {
    val state by viewModel.hotelSearchState.collectAsState()
    var nextPage by rememberSaveable { mutableStateOf("") }
    var hotelList by rememberSaveable { mutableStateOf(mutableListOf<Property>()) }
    var message by remember { mutableStateOf("") }
    var searchHotels by rememberSaveable { mutableStateOf<String?>(null) }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var adultCount by remember { mutableIntStateOf(1) }
    var childCount by remember { mutableIntStateOf(0) }
    var childAgeListValue = remember { mutableStateListOf<Int>() }
    var searchNow by remember { mutableStateOf(false) }

    if (searchHotels == null)
        CurrentCity({
            searchHotels = it
            searchNow = true
        }, viewModel)

    Column {


        search(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .wrapContentHeight(Alignment.CenterVertically)
                .clip(CircleShape),
            searchHotels.orEmpty(),
            onSearchTravelChange = { searchHotels = it },
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            ArrudeiaDateRangeHotel(
                Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                startDate, endDate
            ) { startDateParam, endDateParam ->
                startDate = startDateParam
                endDate = endDateParam
            }

            ArrudeiaPeopleCount(
                Modifier
                    .weight(0.2f),
                adultCount, childCount,
                { adultValue, childValue ->
                    adultCount = adultValue
                    childCount = childValue
                },
                maxCount = 6,
                {
                    childAgeListValue.clear()
                    childAgeListValue.addAll(it)
                }
            )
        }

        ArrudeiaButtonColor(
            onClick = {
                searchNow = true

            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colorButton = colorResource(R.color.colorPrimary),
        ) {
            Text(
                text = stringResource(id = com.arrudeia.core.common.R.string.search),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )
        }
        var childAgesConcat = ""
        if (searchNow) {
            childAgeListValue.forEach {
                childAgesConcat = if (childAgesConcat.isEmpty())
                    it.toString()
                else
                    "$childAgesConcat,$it"
            }
            viewModel.searchHotels(
                searchHotels.orEmpty(),
                startDate,
                endDate,
                adultCount,
                childCount,
                nextPage,
                childAgesConcat
            )
            searchNow = false
        }
        LaunchedEffect(message) {
            if (message.isNotEmpty())
                onShowSnackbar(message, "")
        }

        when (state) {
            is HomeViewModel.HotelSearchState.Loading -> {
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

            is HomeViewModel.HotelSearchState.Success -> {
                searchNow = false
                val result = (state as HomeViewModel.HotelSearchState.Success).data
                result?.properties?.let {
                    hotelList.clear() // so dar clear se for search now se for relamente mantem
                    hotelList.addAll(it.filter { it.images?.isNotEmpty() == true })
                    nextPage = result.serpapi_pagination.next_page_token.orEmpty()
                }
            }

            is HomeViewModel.HotelSearchState.Error -> {
                message = (state as HomeViewModel.HotelSearchState.Error).message

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
        if (hotelList.isNotEmpty())
            HotelItem(hotelList) {
                val param = HotelDetailParam(
                    query = searchHotels.orEmpty(),
                    checkInDate = startDate,
                    checkOutDate = endDate,
                    adults = adultCount,
                    children = childCount,
                    childrenAges = childAgesConcat,
                    propertyToken = it.property_token.orEmpty(),
                    it.amenities.orEmpty()
                )
                onHotelDetailsClick(param)
            }
    }


}
