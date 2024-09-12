package com.arrudeia.feature.home.presentation.ui.events.detail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.common.R.string.no_tickets_available
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.domain.util.toCurrencyReal
import com.arrudeia.feature.home.data.entity.events.LinkType
import com.arrudeia.feature.home.data.entity.events.TicketInfo
import com.arrudeia.feature.home.presentation.model.TravelUIModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder


@Composable
fun EventTicketsItem(tickets: List<TicketInfo>?) {
    val context = LocalContext.current

    val ticketTranslated = LinkType.getStringFromEnum(context, LinkType.TICKETS)

    var filterList = tickets?.filter {
        it.link_type?.lowercase().orEmpty().contentEquals(ticketTranslated?.lowercase().orEmpty())
    }.orEmpty()

    if (filterList.isEmpty())
        Box(Modifier.fillMaxSize()) {
            Text(
                text = stringResource(id = no_tickets_available),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                Color.Black
            )
        }
    else
        LazyColumn() {
            items(filterList.size) { i ->

                Card(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(6.dp)
                            .clickable { filterList[i].link?.openLinkInChrome(context) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HotelOfferOptionItemContent(
                            filterList[i].source.orEmpty(), Modifier
                                .padding(2.dp)
                                .weight(0.8f)
                        )

                        Icon(
                            Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.Black,
                        )

                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
}


fun String.openLinkInChrome(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(this)).apply {
        setPackage("com.android.chrome")
    }
    context.startActivity(intent)
}


@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun HotelOfferOptionItemImage(image: String) {

    GlideImage(
        loading = placeholder(
            ColorDrawable(
                colorResource(
                    id = R.color.background_grey_F7F7F9
                ).toArgb()
            )
        ),
        model = image,
        contentDescription = null,
        modifier = Modifier.size(20.dp),
        contentScale = ContentScale.Fit,
    )
}

@Composable
private fun button(item: TravelUIModel, modifier: Modifier) {
    SuggestionChip(
        modifier = modifier,
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = colorResource(
                R.color.colorPrimary
            )
        ),
        border = SuggestionChipDefaults.suggestionChipBorder(
            borderColor = Color.Transparent,
            enabled = true
        ),
        onClick = { },
        label = {
            Text(item.price.toCurrencyReal(), fontSize = 11.sp, color = Color.White)
        }
    )
}


@Composable
private fun HotelOfferOptionItemContent(item: String, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = item,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 6.dp, end = 2.dp)
        )


    }


}