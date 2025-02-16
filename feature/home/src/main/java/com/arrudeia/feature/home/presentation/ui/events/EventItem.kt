package com.arrudeia.feature.home.presentation.ui.events

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.feature.home.data.entity.events.GoogleEventsResult
import com.arrudeia.feature.home.presentation.navigation.param.EventDetailParam
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
fun EventItem(
    hotelList: List<GoogleEventsResult>,
    onClick: (EventDetailParam) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {

        if (hotelList.isNotEmpty()) {

            val GRID_CELLS_COUNT = 2
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(start = 16.dp, top = 10.dp, end = 16.dp)
                    .fillMaxSize()
                    .clipToBounds(),
                columns = GridCells.Fixed(GRID_CELLS_COUNT),
                contentPadding = PaddingValues(horizontal = 4.dp),
            ) {
                items(hotelList) {

                    Spacer(modifier = Modifier.size(4.dp))
                    EventBoxItem(
                        it,
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                val param = EventDetailParam()
                                param.setParam(it)
                                onClick(param)
                            })
                }

            }
            Spacer(modifier = Modifier.height(40.dp))
        } else
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = com.arrudeia.feature.home.R.string.empty_list_places),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        Spacer(modifier = Modifier.height(40.dp))
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun EventBoxItem(item: GoogleEventsResult, modifier: Modifier) {

    Column() {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
            ) {

                Card(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    GlideImage(
                        model = item.image.orEmpty(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }

                EventBoxContent(
                    item, Modifier.padding(
                        top = 6.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun EventBoxContent(item: GoogleEventsResult, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = item?.title.orEmpty(),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = 1,
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Text(
                    item.date?.`when`.orEmpty(),
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 2.dp),
                    maxLines = 2,
                )
                item.address?.forEach { addressItem ->
                    Text(
                        addressItem.orEmpty(),
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 2.dp),
                        maxLines = 3,
                    )
                }
            }
        }
    }
}
