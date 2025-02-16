package com.arrudeia.feature.home.presentation.ui.events.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.common.R.string.include
import com.arrudeia.core.common.R.string.more_info_on
import com.arrudeia.core.common.R.string.optional
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.feature.home.data.entity.events.GoogleEventsResult
import com.arrudeia.feature.home.data.entity.events.LinkType
import com.arrudeia.feature.trip.presentation.model.TripUIModel

@Composable
fun EventDetailContent(
    item: GoogleEventsResult,
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
                EventContentDescription(item)
            }
        }
    }
}

@Composable
private fun EventContentDescription(item: GoogleEventsResult) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxSize()
            .clipToBounds(),
    ) {
        items(1) {
            EventBodyContentDescription(item)
            Spacer(modifier = Modifier.size(50.dp))
        }
    }
}

@Composable
private fun EventBodyContentDescription(item: GoogleEventsResult) {
    /*  Text(
          text = stringResource(description),
          color = Color.Black,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Start
      )
      Spacer(modifier = Modifier.size(10.dp))*/
    val context = LocalContext.current
    val moreInfoTranslated = LinkType.getStringFromEnum(context, LinkType.MORE_INFO)
    var address = ""
    item?.address?.forEach {
        if (address.isEmpty())
            address = it
        else
            address.plus(" - ").plus(it)
    }


    Row(
        modifier = Modifier
            .padding(bottom = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Default.PinDrop,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(22.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = address,
            color = Color.Black,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
        )
    }

    Row(
        modifier = Modifier
            .padding(bottom = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(22.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = item?.date?.start_date.orEmpty() + " - " + ", " + item?.date?.`when`?.orEmpty(),
            color = Color.Black,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
        )
    }

    val moreInfo = item.ticket_info?.filter {
        it.link_type.contentEquals(
            moreInfoTranslated
        )
    }?.firstOrNull()

    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black, fontSize = 14.sp)) {
            append(item?.description.orEmpty())
        }
        withStyle(
            style = SpanStyle(
                color = colorResource(id = colorPrimary),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(stringResource(id = more_info_on) + " " + moreInfo?.source.orEmpty())
        }
    }

    Text(
        text = annotatedText,
        modifier = Modifier.clickable { moreInfo?.link.orEmpty().openLinkInChrome(context) }
    )

}

@Composable
fun HotelHeaderContentDescription(item: GoogleEventsResult) {
    Text(
        text = item.title,
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    )

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