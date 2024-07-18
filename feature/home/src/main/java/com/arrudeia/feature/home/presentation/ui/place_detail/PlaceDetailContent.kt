package com.arrudeia.feature.home.presentation.ui.place_detail

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.domain.util.toCurrencyReal
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaPlaceDetailsUiModel
import com.arrudeia.feature.trip.R.string.description
import com.arrudeia.feature.trip.R.string.from
import com.arrudeia.feature.trip.presentation.model.TripUIModel

@Composable
fun content(
    item: ArrudeiaPlaceDetailsUiModel,
) {
    Column(
        modifier = Modifier
            .padding(top = 260.dp)
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
                contentDescription(item)

            }
        }
    }
}

@Composable
private fun contentDescription(item: ArrudeiaPlaceDetailsUiModel) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .clipToBounds(),
    ) {
        items(1) {
            Text(
                text = item?.name.orEmpty(),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            headerContentDescription(item)
            Spacer(modifier = Modifier.size(20.dp))
            bodyContentDescription(item)
            Spacer(modifier = Modifier.size(50.dp))
        }
    }
}

@Composable
private fun bodyContentDescription(item: ArrudeiaPlaceDetailsUiModel) {
    Text(
        text = stringResource(description),
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.size(10.dp))
    Text(
        text = item?.description.orEmpty(),
        color = Color.Gray,
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
    )
    Spacer(modifier = Modifier.size(10.dp))
    Availables(item)
}

@Composable
private fun headerContentDescription(item: ArrudeiaPlaceDetailsUiModel) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart),
            text = item?.shortLocation().orEmpty(),
            color = Color.Gray,
            fontSize = 14.sp,
            textAlign = TextAlign.Start
        )
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {


            Row(modifier = Modifier) {

                val priceBadge = if (item.priceLevel != null) item.priceLevel else 0
                for (i in 1 until 4) {
                    Image(
                        painter = painterResource(R.drawable.paid_24px),
                        contentDescription = null,
                        colorFilter = if (i <= priceBadge!!) ColorFilter.tint(colorResource(id = colorPrimary)) else ColorFilter.tint(
                            Color.Gray
                        ),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(18.dp)
                    )


                }
            }
            Row(modifier = Modifier) {

                val ratingBadge = if (item.rating != null) item.rating else 0
                for (i in 1 until 4) {
                    Image(
                        painter = painterResource(R.drawable.ic_star),
                        contentDescription = null,
                        colorFilter = if (i <= ratingBadge!!) ColorFilter.tint(colorResource(id = colorPrimary)) else ColorFilter.tint(
                            Color.Gray
                        ),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(18.dp)
                    )


                }
            }

        }
    }
}


@Composable
private fun optional(item: TripUIModel?) {
    if (item?.optional.orEmpty().isNotEmpty()) {
        Text(
            text = stringResource(com.arrudeia.feature.trip.R.string.optional),
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

@Composable
private fun Availables(item: ArrudeiaPlaceDetailsUiModel) {
    val context = LocalContext.current
    if (item.available?.isNotEmpty() == true) {
        Text(
            text = stringResource(com.arrudeia.feature.trip.R.string.include),
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
            item.available?.forEach {
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
                        text = it.available.getString(context),
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}