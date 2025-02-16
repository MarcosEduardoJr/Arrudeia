package com.arrudeia.feature.home.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.ImagePagerWithDots
import com.arrudeia.feature.home.data.entity.hotel.Property


@Composable
fun HotelItem(
    hotelList: List<Property>,
    onPropertyClick: (Property) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {

        if (hotelList.isNotEmpty()) {

            val GRID_CELLS_COUNT = 1
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
                    HotelBoxItem(
                        it,
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                onPropertyClick(it)
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


@Composable
private fun HotelBoxItem(item: Property, modifier: Modifier) {

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
                    .padding(10.dp)
            ) {
                ImagePagerWithDots(
                    item.images?.map { it.original_image.orEmpty() }?.toList().orEmpty(),
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    maxDots = 3,
                  cardCornerShape =   8.dp
                )
                HotelBoxContent(
                    item, Modifier.padding(
                        top = 6.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun HotelBoxContent(item: Property, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = item?.name.orEmpty(),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                val priceBadge = if (item.overall_rating != null) item.overall_rating.toInt() else 0
                if (priceBadge != 0) {
                    Text(
                        "$priceBadge",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(end = 2.dp)
                    )
                    for (i in 0 until priceBadge) {
                        Image(
                            painter = painterResource(R.drawable.ic_star),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(colorResource(id = R.color.colorPrimary)),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .size(14.dp)
                        )
                    }
                    Text(
                        "(${item.reviews})",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 2.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Text(
                item.total_rate?.lowest.orEmpty(),
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 2.dp)
            )
        }
    }
}
