package com.arrudeia.feature.home.presentation.ui

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.domain.util.toCurrencyReal
import com.arrudeia.feature.home.R
import com.arrudeia.feature.home.presentation.model.TravelUIModel
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import com.arrudeia.feature.home.presentation.viewmodel.TravelUiState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.drawable.ic_pin
import com.arrudeia.core.designsystem.R.drawable.ic_calendar
import com.arrudeia.core.designsystem.R.color.colorPrimary

@Composable
fun travels(
    viewModel: HomeViewModel,
    searchTravel: String,
    onTripDetailClick: (String) -> Unit,
    modifier: Modifier
) {
    Spacer(modifier = Modifier.size(20.dp))
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = stringResource(R.string.near_to_you),
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start
    )
    val uiState by viewModel.travelSharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchDataTravels()
    when (uiState) {
        is TravelUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = modifier
            )
        }

        is TravelUiState.Error -> {
            Text(
                text = stringResource((uiState as TravelUiState.Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is TravelUiState.Success -> {
            var list = (uiState as TravelUiState.Success).list
            list = filterSearchList(searchTravel, list)
            LazyColumn() {
                items(items = list.toList(), itemContent = {
                    Spacer(modifier = Modifier.size(8.dp))
                    travelItem(
                        it,
                        Modifier.clickable { onTripDetailClick(it.id.toString()) })
                })
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

        else -> {
        }
    }
}

@Composable
fun travelItem(item: TravelUIModel, modifier: Modifier) {

    Column() {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                image(item)
                content(
                    item, Modifier
                        .weight(1f)
                        .padding(6.dp)
                )
                button(
                    item, Modifier
                        .height(18.dp)
                        .align(Alignment.Top)
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun image(item: TravelUIModel) {
    Card(
        shape = RoundedCornerShape(12.dp)
    ) {
        GlideImage(
            loading = placeholder(
                ColorDrawable(
                    colorResource(
                        id = background_grey_F7F7F9
                    ).toArgb()
                )
            ),
            model = item.coverImageUrl,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun button(item: TravelUIModel, modifier: Modifier) {
    SuggestionChip(
        modifier = modifier,
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = colorResource(
                colorPrimary
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
private fun content(item: TravelUIModel, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = item.name,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(ic_pin),
                contentDescription = null,
                modifier = Modifier.padding(end = 2.dp)
            )
            Text(
                text = item.shortLocation(),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(ic_calendar),
                contentDescription = null,
                modifier = Modifier.padding(end = 2.dp)
            )
            Text(
                text = item.date(),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
