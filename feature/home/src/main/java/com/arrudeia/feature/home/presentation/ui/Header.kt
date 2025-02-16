package com.arrudeia.feature.home.presentation.ui

import android.Manifest
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R.color.colorBlack
import com.arrudeia.core.designsystem.component.HtmlText
import com.arrudeia.core.ui.hasFineLocationPermission
import com.arrudeia.core.ui.hasNotificationPermission
import com.arrudeia.core.utils.ShowPermissionLocationDialog
import com.arrudeia.feature.home.R.string.messageHeaderHome
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

private const val SPACING_FIX = 3f

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun header(
    modifier: Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onRouteClick: (String) -> Unit,
    viewModel: HomeViewModel,
) {
    var user  = viewModel.user

    Box(
        modifier = modifier
    ) {

        HtmlText(
            modifier = Modifier
                .align(Alignment.CenterStart),
            html = stringResource(id = messageHeaderHome),
            normalColor = colorBlack,
            alignmentText = View.TEXT_ALIGNMENT_TEXT_START,
            textStyle = MaterialTheme.typography.titleMedium
        )
        /*
        imageHeader(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterEnd)
                .clickable { onRouteClick(profileRoute) },
            user.value?.image.orEmpty(),
            placeholder( ic_arrudeia_logo),
            null
        )*/
    }
}

fun getCurrentCity(context: Context, callback: (String?, String?) -> Unit) {

    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val locationTask: Task<Location> = fusedLocationClient.lastLocation
    locationTask.addOnSuccessListener { location: Location? ->
        location?.let {
            val geocoder = Geocoder(context)
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val cityName = addresses[0].subAdminArea.orEmpty()
                callback(cityName, addresses[0].countryCode.orEmpty())
            } else {
                callback(null, null)
            }
        } ?: callback(null, null)
    }.addOnFailureListener {
        callback(null, null)
    }
}

@Composable
fun CurrentCity(
    currentCityChange: (String) -> Unit,
    viewModel: HomeViewModel
) {
    val context = LocalContext.current
    var needPermission by rememberSaveable { mutableStateOf(true) }

    if (needPermission)
        ShowPermissionLocationDialog({ needPermission = it })

    if (needPermission.not()) {
        if (viewModel.stateChoosed.value == null)
            getCurrentCity(context) { currentState, currentCountry ->
                currentCityChange(currentState.orEmpty())
            }
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


private fun ActivityResultLauncher<Array<String>>.requestArrudeiaPermissions(
    context: Context
) {
    val hasLocationPermission = context.hasFineLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    when {
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermissions)
        }

        !hasLocationPermission -> launch(locationPermissions)

    }
}