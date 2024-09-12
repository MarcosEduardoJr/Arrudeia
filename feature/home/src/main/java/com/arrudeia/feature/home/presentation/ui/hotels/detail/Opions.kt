package com.arrudeia.feature.home.presentation.ui.hotels.detail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.domain.util.toCurrencyReal
import com.arrudeia.feature.home.data.entity.hotel.FeaturedPrice
import com.arrudeia.feature.home.presentation.model.TravelUIModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder


@Composable
fun HotelOfferOptionItem(listOffers: List<FeaturedPrice>) {
    val context = LocalContext.current
    var listResult = listOffers.sortedBy { it.total_rate?.extracted_lowest }
    LazyColumn() {
        items(listResult.size) { i ->

            Card(
                modifier = Modifier.padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(6.dp)
                        .clickable { listResult[i].link?.openLinkInChrome(context) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HotelOfferOptionItemImage(listResult[i].logo.orEmpty())
                    HotelOfferOptionItemContent(
                        listResult[i], Modifier
                            .padding(2.dp)
                            .weight(0.8f)
                    )

                    Text(
                        text = listResult[i].total_rate?.lowest.orEmpty(),
                        fontSize = 16.sp,
                        color = Color.Black,
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
private fun HotelOfferOptionItemContent(item: FeaturedPrice, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = item.source.orEmpty(),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 6.dp, end = 2.dp)
        )


    }


}