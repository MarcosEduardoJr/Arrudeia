package com.arrudeia.feature.receipt.presentation.ui

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.common.R.string.time
import com.arrudeia.feature.receipt.presentation.model.ReceiptUIModel
import com.arrudeia.feature.receipt.presentation.navigation.param.ReceiptDetailParam
import com.arrudeia.feature.receipt.presentation.viewmodel.ReceiptViewModel
import com.arrudeia.feature.receipt.presentation.viewmodel.ReceiptsUiState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Composable
fun receipt(
    viewModel: ReceiptViewModel,
    searchTravel: String,
    modifier: Modifier,
    onReceiptDetailClick: (ReceiptDetailParam) -> Unit,
) {
    Spacer(modifier = Modifier.size(20.dp))
    val uiState by viewModel.sharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchData()
    when (uiState) {
        is ReceiptsUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = modifier
            )
        }

        is ReceiptsUiState.Error -> {
            Text(
                text = stringResource((uiState as ReceiptsUiState.Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is ReceiptsUiState.Success -> {
            val GRID_CELLS_COUNT = 2
            var list = (uiState as ReceiptsUiState.Success).list
            list = filterSearchList(searchTravel, list)
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .clipToBounds(),
                columns = GridCells.Fixed(GRID_CELLS_COUNT),
                contentPadding = PaddingValues(horizontal = 4.dp),
            ) {
                items(list?.toList().orEmpty()) {
                    Spacer(modifier = Modifier.size(4.dp))
                    travelItem(
                        it,
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable { onReceiptDetailClick(ReceiptDetailParam(it?.uuid.toString())) })
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

        else -> {
        }
    }
}

@Composable
fun travelItem(item: ReceiptUIModel?, modifier: Modifier) {

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
                    .padding(16.dp)
            ) {

                image(item)
                ReceiptDetailContent(
                    item, Modifier
                )
                button(
                    item, Modifier
                        .height(18.dp)
                        .align(Alignment.Start)
                        .padding(top = 2.dp)
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun image(item: ReceiptUIModel?) {
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
            model = item?.img.orEmpty(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(100.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun button(item: ReceiptUIModel?, modifier: Modifier) {
    val context = LocalContext.current
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
            Text(
                ReceiptLevelEnum.getStringFromEnum(
                    context,
                    ReceiptLevelEnum.valueOf(item?.level.orEmpty())
                ),
                fontSize = 11.sp,
                color = Color.White
            )
        }
    )
}

@Composable
private fun ReceiptDetailContent(item: ReceiptUIModel?, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = item?.name.orEmpty(),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black
        )
        Text(
            text = item?.quantity.orEmpty(),
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = stringResource(id = time).plus(" " + item?.getTimeFormatted().orEmpty()),
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
