package com.arrudeia.feature.aid.presentation.ui

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.feature.aid.presentation.model.AidUIModel
import com.arrudeia.feature.aid.presentation.navigation.param.AidDetailParam
import com.arrudeia.feature.aid.presentation.viewmodel.AidViewModel
import com.arrudeia.feature.aid.presentation.viewmodel.AidsUiState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Composable
fun aid(
    viewModel: AidViewModel,
    searchTravel: String,
    modifier: Modifier,
    onReceiptDetailClick: (AidDetailParam) -> Unit,
) {
    Spacer(modifier = Modifier.size(20.dp))
    val uiState by viewModel.sharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchData()
    when (uiState) {
        is AidsUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = modifier
            )
        }

        is AidsUiState.Error -> {
            Text(
                text = stringResource((uiState as AidsUiState.Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is AidsUiState.Success -> {
            val GRID_CELLS_COUNT = 2
            var list = (uiState as AidsUiState.Success).list
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
                            .clickable { onReceiptDetailClick(AidDetailParam(it?.id.toString())) })
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

        else -> {
        }
    }
}

@Composable
fun travelItem(item: AidUIModel?, modifier: Modifier) {

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
                    item, Modifier.padding(
                        top = 6.dp
                    )
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun image(item: AidUIModel?) {
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
private fun ReceiptDetailContent(item: AidUIModel?, modifier: Modifier) {
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
    }
}