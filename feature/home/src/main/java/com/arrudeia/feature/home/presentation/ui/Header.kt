package com.arrudeia.feature.home.presentation.ui

import android.Manifest
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.data.navigation.arrudeiaRoute
import com.arrudeia.core.data.navigation.profileRoute
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.ArrudeiaDialog
import com.arrudeia.core.designsystem.component.ArrudeiaOutlinedActionButton
import com.arrudeia.core.ui.hasFineLocationPermission
import com.arrudeia.core.ui.hasNotificationPermission
import com.arrudeia.core.ui.showShouldLocationPermission
import com.arrudeia.feature.home.R.string.need_know_location_permission
import com.arrudeia.feature.home.R.string.permission_required
import com.arrudeia.feature.home.R.string.yes
import com.arrudeia.feature.home.presentation.model.StateUIModel
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import com.arrudeia.feature.home.presentation.viewmodel.ProfileUiState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.bumptech.glide.integration.compose.placeholder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

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


        cityDrop(modifier = Modifier.align(Alignment.Center), onShowSnackbar, viewModel)

        imageHeader(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterEnd)
                .clickable { onRouteClick(profileRoute) },
            image,
            placeholder(painterResource(id = R.drawable.ic_profile_edit)),
            null
        )
        /*      imageHeader(
                  modifier = Modifier
                      .size(37.dp)
                      .align(Alignment.CenterEnd)
                      .clickable { onRouteClick(arrudeiaRoute) },
                  String(),
                  placeholder(painterResource(id = R.drawable.ic_map_search)),
                  ColorFilter.tint(colorResource(id = R.color.colorPrimary))
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
                val cityName = addresses[0].adminArea.orEmpty()
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
fun cityDrop(
    modifier: Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: HomeViewModel
) {


    val context = LocalContext.current
    val activity = context as ComponentActivity
    var showCityDrop by rememberSaveable { mutableStateOf(true) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        showCityDrop = true
    }



    if (activity.showShouldLocationPermission()) {
        showCityDrop = false
        ArrudeiaDialog(
            title = stringResource(id = permission_required),
            onDismiss = { /* Normal dismissing not allowed for permissions */ },
            description =
            stringResource(id = need_know_location_permission),
            primaryButton = {
                ArrudeiaOutlinedActionButton(
                    text = stringResource(id = yes),
                    isLoading = false,
                    onClick = {
                        permissionLauncher.requestArrudeiaPermissions(context)
                    },
                )
            }
        )
    }
    if (showCityDrop) {

        var showCityDropList by rememberSaveable { mutableStateOf(false) }


        if (viewModel.stateChoosed.value == null)
            getCurrentCity(context) { currentState, currentCountry ->
                viewModel.stateChoosed.value = StateUIModel(currentState.orEmpty())
                viewModel.country.value = currentCountry.orEmpty()
                if (viewModel.loading.value.not())
                    viewModel.fetchStatesByCountry(viewModel.country.value)
            }
        Row(modifier = modifier.clickable {
            showCityDropList = true
        })
        {
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
            Text(
                text = viewModel.stateChoosed.value?.name.orEmpty(),
                modifier = Modifier.padding(4.dp),
                color = Color.Black
            )
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

            if (showCityDropList)
                DropdownMenu(
                    expanded = showCityDropList,
                    onDismissRequest = { showCityDropList = false },
                    modifier = Modifier
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(horizontal = 8.dp)
                    ) {
                        viewModel.states.value.forEach { state ->

                            Text(
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(bottom = 2.dp)
                                    .clickable {
                                        showCityDropList = false
                                        viewModel.stateChoosed.value = StateUIModel(state.name)
                                        viewModel.fetchPlaces()
                                    },
                                text = state.name,
                                color = Color.Black
                            )

                        }
                    }
                }
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