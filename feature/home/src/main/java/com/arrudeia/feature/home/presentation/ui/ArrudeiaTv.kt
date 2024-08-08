package com.arrudeia.feature.home.presentation.ui

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.feature.home.R
import com.arrudeia.feature.home.presentation.model.ArrudeiaTvUIModel
import com.arrudeia.feature.home.presentation.viewmodel.ArrudeiaTvUiState
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Composable
fun arrudeiaTv(
    viewModel: HomeViewModel,
    onStoriesClick: (String) -> Unit,
    modifier: Modifier
) {

    val arrTvUiState by viewModel.arrTvSharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchDataArrTv()
    when (arrTvUiState) {
        is ArrudeiaTvUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }

        is ArrudeiaTvUiState.Error -> {
            Text(
                text = stringResource((arrTvUiState as ArrudeiaTvUiState.Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is ArrudeiaTvUiState.Success -> {
            Box(
                modifier = modifier
            ) {
                val list = (arrTvUiState as ArrudeiaTvUiState.Success).list
                LazyRow(modifier = Modifier.fillMaxHeight()) {
                    items(items = list.toList(), itemContent = {
                        Spacer(modifier = Modifier.size(8.dp))
                        arrudeiaTvItem(
                            Modifier
                                .align(Alignment.Center)
                                .clickable {
                                    onStoriesClick(it.id.toString())
                                }, it
                        )

                    })
                }
            }
        }

        else -> {
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun arrudeiaTvItem(modifier: Modifier, item: ArrudeiaTvUIModel) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(percent = 50),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(64.dp)
                .background(color = colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary))
                .fillMaxSize()
        ) {
            GlideImage(
                loading = placeholder(
                    ColorDrawable(
                        colorResource(
                            id = com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
                        ).toArgb()
                    )
                ),
                model = item.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        }
    }
}

