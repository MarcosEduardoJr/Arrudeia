package com.arrudeia.feature.home.presentation.ui.place_detail

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
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaPlaceDetailsUiModel
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
internal fun PlacesDetailRoute(
    onBackClick: () -> Unit,
    placeDetail: ArrudeiaPlaceDetailsUiModel
) {
    PlaceDetail(
        placeDetail,
        onBackClick
    )

}

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("DesignSystem")
@Composable
internal fun PlaceDetail(item: ArrudeiaPlaceDetailsUiModel, onBackClick: () -> Unit) {
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
                        model = item?.image,
                        contentDescription = null,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                    )
                    content(item = item)
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
