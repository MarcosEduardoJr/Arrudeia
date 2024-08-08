package com.arrudeia.feature.trip.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.trip.R.string.whatsapp_contact_message
import com.arrudeia.feature.trip.R.string.whatsapp_message
import com.arrudeia.feature.trip.presentation.model.TripUIModel
import com.arrudeia.feature.trip.presentation.viewmodel.TripDetailViewModel
import com.arrudeia.feature.trip.presentation.viewmodel.TripDetailViewModel.TripDetailUiState.Error
import com.arrudeia.feature.trip.presentation.viewmodel.TripDetailViewModel.TripDetailUiState.Loading
import com.arrudeia.feature.trip.presentation.viewmodel.TripDetailViewModel.TripDetailUiState.Success
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
internal fun tripDetailRoute(
    viewModel: TripDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val arrTvUiState by viewModel.travelSharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchData()
    when (arrTvUiState) {
        is Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is Error -> {
            Text(
                text = stringResource((arrTvUiState as Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is Success -> {
            val item = (arrTvUiState as Success).item
            tripDetail(
                item,
                onBackClick
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("DesignSystem")
@Composable
internal fun tripDetail(item: TripUIModel?, onBackClick: () -> Unit) {
    val context = LocalContext.current
    val url = context.getString(
        whatsapp_message, item?.whatsapp.orEmpty(),
        context.getString(
            whatsapp_contact_message,
            item?.shortLocation().toString(),
            item?.date().orEmpty()
        )
    )
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    ArrudeiaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorResource(id = background_grey_F7F7F9)
            ) {
                Box(
                    modifier = with(Modifier) {
                        fillMaxSize()
                    })
                {
                    GlideImage(
                        model = item?.coverImageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                    )
                 content(item = item, intent =intent )
                }
            }
            buttonBottom(
                onBackClick, Modifier.Companion
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun buttonBottom(onBackClick: () -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
    ) {

        CircularIconButton(
            onClick = {
                onBackClick()
            },
            icon = Icons.Rounded.ArrowBack,
            backgroundColor = colorResource(id = background_grey_F7F7F9),
            iconSize = 50.dp,
            modifier = Modifier
        )
    }
}
