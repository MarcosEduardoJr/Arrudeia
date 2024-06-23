package com.arrudeia.feature.home.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.data.navigation.arrudeiaRoute
import com.arrudeia.core.data.navigation.profileRoute
import com.arrudeia.core.designsystem.R
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import com.arrudeia.feature.home.presentation.viewmodel.ProfileUiState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.bumptech.glide.integration.compose.placeholder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun header(
    modifier: Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onRouteClick: (String) -> Unit,
    viewModel: HomeViewModel,
) {
    val uiState by viewModel.userSharedFlow.collectAsStateWithLifecycle()
    var image by rememberSaveable { mutableStateOf("") }
    when (uiState) {
        is ProfileUiState.Success -> {
            image =
                (uiState as ProfileUiState.Success).data.image.orEmpty()
        }

        else -> {}
    }
    Box(
        modifier = modifier
    ) {
        imageHeader(
            modifier = Modifier
                .size(37.dp)
                .align(Alignment.CenterStart)
                .clickable { onRouteClick(profileRoute) },
            image,
            placeholder(painterResource(id = R.drawable.ic_profile_edit)),
            null
        )

        cityDrop(modifier = Modifier.align(Alignment.Center), onShowSnackbar)

        imageHeader(
            modifier = Modifier
                .size(37.dp)
                .align(Alignment.CenterEnd)
                .clickable { onRouteClick(arrudeiaRoute) },
            String(),
            placeholder(painterResource(id = R.drawable.ic_map_search)),
            ColorFilter.tint(colorResource(id = R.color.colorPrimary))
        )
    }
}

@Composable
fun cityDrop(modifier: Modifier, onShowSnackbar: suspend (String, String?) -> Boolean) {
    val message = stringResource(id = com.arrudeia.feature.home.R.string.travels_only_this_city)
    Row(modifier = modifier.clickable {
        CoroutineScope(Dispatchers.IO).launch {
            onShowSnackbar(message, "")
        }
    }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_pin),
            contentDescription = null,
            modifier = Modifier
                .align(
                    Alignment.CenterVertically
                )
                .size(12.dp),
            tint = colorResource(id = R.color.colorBlack)
        )
        Text(text = "Recife, PE", modifier = Modifier.padding(4.dp), color = Color.Black)
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = null,
            modifier = Modifier
                .align(
                    Alignment.CenterVertically
                )
                .size(12.dp),
            tint = colorResource(id = R.color.colorBlack)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun imageHeader(
    modifier: Modifier,
    image: String,
    placeholder: Placeholder,
    colorIcon: ColorFilter?
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(37.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(color = Color.White)
                .fillMaxSize()
        ) {
            GlideImage(
                loading = placeholder,
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape),
                failure = placeholder,
                colorFilter = colorIcon
            )
        }
    }
}