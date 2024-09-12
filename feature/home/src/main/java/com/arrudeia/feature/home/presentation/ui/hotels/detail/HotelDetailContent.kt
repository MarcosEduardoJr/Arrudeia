package com.arrudeia.feature.home.presentation.ui.hotels.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.common.R.string.include
import com.arrudeia.core.common.R.string.optional
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.feature.home.R.string.hour_of_check_in
import com.arrudeia.feature.home.R.string.check_out
import com.arrudeia.feature.home.data.entity.hotel.HotelDetailResponse
import com.arrudeia.feature.trip.presentation.model.TripUIModel

@Composable
fun HotelDetailContent(
    item: HotelDetailResponse,
    amenities: List<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds(),
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.background_grey_F7F7F9),
                contentColor = colorResource(id = R.color.background_grey_F7F7F9),
                disabledContainerColor = colorResource(id = R.color.background_grey_F7F7F9),
                disabledContentColor = colorResource(id = R.color.background_grey_F7F7F9)
            )
        ) {
            Box(
                modifier = with(Modifier) {
                    fillMaxSize()
                })
            {
                HotelContentDescription(item, amenities)
            }
        }
    }
}

@Composable
private fun HotelContentDescription(item: HotelDetailResponse, amenities: List<String>) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxSize()
            .clipToBounds(),
    ) {
        items(1) {
            HotelBodyContentDescription(item, amenities)
            Spacer(modifier = Modifier.size(50.dp))
        }
    }
}

@Composable
private fun HotelBodyContentDescription(item: HotelDetailResponse, amenities: List<String>) {
    /*  Text(
          text = stringResource(description),
          color = Color.Black,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Start
      )
      Spacer(modifier = Modifier.size(10.dp))*/

    Text(
        text = item?.address.orEmpty(),
        color = Color.Black,
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
    )


    Text(
        text = stringResource(id = hour_of_check_in) + ": " + item?.check_in_time.orEmpty() + " - " + stringResource(
            id = check_out
        ) + ": " + item?.check_out_time.orEmpty(),
        color = Color.Black,
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
    )


    Text(
        text = item?.phone.orEmpty(),
        color = Color.Black,
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
    )

    Spacer(modifier = Modifier.size(10.dp))

    Availables(amenities)
}

@Composable
fun HotelHeaderContentDescription(item: HotelDetailResponse) {
    Text(
        text = item?.name.orEmpty(),
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            val priceBadge =
                if (item.overall_rating != null) item.overall_rating.toInt() else 0
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


@Composable
private fun optional(item: TripUIModel?) {
    if (item?.optional.orEmpty().isNotEmpty()) {
        Text(
            text = stringResource(optional),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clipToBounds(),
        ) {
            item?.optional.orEmpty().forEach {
                Row {
                    Image(
                        Icons.Rounded.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        colorFilter = ColorFilter.tint(color = colorResource(id = colorPrimary))
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = it,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

@Composable
private fun Availables(amenities: List<String>) {
    val context = LocalContext.current
    if (amenities?.isNotEmpty() == true) {
        Text(
            text = stringResource(include),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .fillMaxWidth(),
        ) {
            amenities?.forEach {
                Row {
                    Image(
                        Icons.Rounded.Check,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        colorFilter = ColorFilter.tint(color = colorResource(id = R.color.colorPrimary))
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = it,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}