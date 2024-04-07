package com.arrudeia.feature.arrudeia.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddLocationAlt
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.component.TextFieldInput
import com.arrudeia.core.network.BuildConfig
import com.arrudeia.feature.arrudeia.R
import com.arrudeia.feature.arrudeia.model.ArrudeiaAvailablePlace
import com.arrudeia.feature.arrudeia.model.ArrudeiaCategoryPlace
import com.arrudeia.feature.arrudeia.model.ArrudeiaPlaceDetails
import com.arrudeia.feature.arrudeia.model.ArrudeiaSubCategoryPlace
import com.arrudeia.feature.arrudeia.ui.MAPS_UTIL.Companion.MAPS_PERMISSIONS
import com.arrudeia.feature.arrudeia.ui.MAPS_UTIL.Companion.PERMISSION_REQUEST_CODE
import com.arrudeia.feature.arrudeia.viewmodel.ArrudeiaViewModel
import com.arrudeia.feature.arrudeia.viewmodel.LocationState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch
import okhttp3.internal.filterList


@Composable
fun ArrudeiaRoute(
    onBackClick: () -> Unit,
    onRouteClick: (String) -> Unit,
    viewModel: ArrudeiaViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val context = LocalContext.current
    val isPermissionsGranted by remember { mutableStateOf(hasRequiredPermissions(context)) }

    if (!isPermissionsGranted) {
        ActivityCompat.requestPermissions(
            context as Activity, MAPS_PERMISSIONS, PERMISSION_REQUEST_CODE
        )
    } else {

        viewModel.fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context as Activity)
        Places.initialize(context.applicationContext, BuildConfig.MAPS_API_KEY)
        viewModel.placesClient = Places.createClient(context)
        viewModel.geoCoder = Geocoder(context)
        LocationScreen(viewModel = viewModel)
    }
}

@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun LocationScreen(modifier: Modifier = Modifier, viewModel: ArrudeiaViewModel) {
    val activity = LocalContext.current as Activity
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            if (locationEnabled(activity)) {
                viewModel.getCurrentLocation()
            } else {
                viewModel.locationState = LocationState.LocationDisabled
            }
        }
    }

    AnimatedContent(
        viewModel.locationState, label = ""
    ) { state ->
        when (state) {
            is LocationState.NoPermission -> {
                Column {
                    Text("We need location permission to continue")
                    Button(onClick = { locationPermissionState.launchMultiplePermissionRequest() }) {
                        Text("Request permission")
                    }
                }
            }

            is LocationState.LocationDisabled -> {
                Column {
                    Text("We need location to continue")
                    Button(onClick = { requestLocationEnable(activity, viewModel) }) {
                        Text("Enable location")
                    }
                }
            }

            is LocationState.LocationLoading -> {
                Text("Loading Map")
            }

            is LocationState.Error -> {
                Column {
                    Text("Error fetching your location")
                    Button(onClick = { viewModel.getCurrentLocation() }) {
                        Text("Retry")
                    }
                }
            }

            is LocationState.LocationAvailable -> {
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(state.cameraLatLang, 15f)
                }

                val mapUiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
                val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
                val scope = rememberCoroutineScope()

                LaunchedEffect(viewModel.currentLatLong) {
                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(viewModel.currentLatLong))
                }

                LaunchedEffect(cameraPositionState.isMoving) {
                    if (!cameraPositionState.isMoving) {
                        viewModel.getAddress(cameraPositionState.position.target)
                    }
                }

                var isPlaceClicked by rememberSaveable { mutableStateOf<ArrudeiaPlaceDetails?>(null) }



                Box(
                    modifier = Modifier.fillMaxSize()
                ) {


                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        uiSettings = mapUiSettings,
                        properties = mapProperties,
                        onMapClick = {
                            scope.launch {
                                cameraPositionState.animate(CameraUpdateFactory.newLatLng(it))
                            }
                        },
                    ) {
                        viewModel.places.forEach { place ->
                            Marker(
                                state = rememberMarkerState(
                                    position = LatLng(
                                        place.location.latitude,
                                        place.location.longitude
                                    )
                                ),
                                draggable = false,
                                title = place.name,
                                onClick = {
                                    isPlaceClicked = place
                                    false
                                }
                            )


                        }
                    }
                    Icon(
                        Icons.Rounded.LocationOn, null, modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        tint = colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary)
                    )

                    if (isPlaceClicked != null)
                        PlaceDetailScreen(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .clickable { isPlaceClicked = null },
                            place = isPlaceClicked!!
                        )
                    else {
                        searchAddress(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth(), viewModel, cameraPositionState
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun searchAddress(
    modifier: Modifier,
    viewModel: ArrudeiaViewModel,
    cameraPositionState: CameraPositionState
) {
    val color = colorResource(id = com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9)
    var addMarker by rememberSaveable { mutableStateOf(false) }
    var saveMarker by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier,
    ) {
        if (saveMarker) {
            viewModel.addPlace(
                ArrudeiaPlaceDetails(
                    "teste",
                    "teste",
                    cameraPositionState.position.target,
                    4.0f,
                    1,
                    listOf()
                )
            )
            addMarker = false
        }
        if (addMarker)
            DialogWithImage(
                { addMarker = false },
                { saveMarker = true },
                viewModel
            )
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
            shape = CircleShape,
            onClick = {
                addMarker = true
            },
            containerColor = colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary),
            contentColor = Color.White
        ) {
            Icon(Icons.Rounded.AddLocationAlt, null)
        }
        Box(
            modifier = Modifier
                .background(
                    color,
                    // rounded corner to match with the OutlinedTextField
                    shape = RoundedCornerShape(4.dp)
                )
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .heightIn(100.dp, 400.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var focusInSearchAddress by remember { mutableStateOf(false) }
                searchAddressInput(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .clip(CircleShape)
                        .onFocusChanged {
                            focusInSearchAddress = it.isFocused
                        }, viewModel
                )

                resultSearchAddress(focusInSearchAddress, viewModel)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogWithImage(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    viewModel: ArrudeiaViewModel,
) {
    var categoryChose by rememberSaveable { mutableStateOf<ArrudeiaCategoryPlace?>(null) }
    var subCategoryChose by rememberSaveable { mutableStateOf<ArrudeiaSubCategoryPlace?>(null) }


    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        containerColor = colorResource(id = com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {

                TextButton(
                    onClick = {
                        if (categoryChose == null) onDismissRequest() else categoryChose = null
                    },
                    modifier = Modifier.padding(end = 16.dp),
                ) {
                    Text(
                        stringResource(R.string.cancel),
                        color = colorResource(id = com.arrudeia.core.designsystem.R.color.text_grey)
                    )
                }
            }

            if (subCategoryChose != null && categoryChose != null)
                categoryChose?.let {
                    subCategoryChose?.let { it1 ->
                        formCategoriesDetail(
                            viewModel, it,
                            it1
                        )
                    }
                }
            else if (categoryChose == null)
                categories(viewModel, onCategoryChose = { categoryChose = it })
            else
                categoryChose?.let { category ->
                    subCategories(
                        category,
                        onSubCategoryChoose = { subCategoryChose = it })
                }

        }

    }

}

@Composable
private fun categories(
    viewModel: ArrudeiaViewModel,
    onCategoryChose: (ArrudeiaCategoryPlace?) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.category_of_place),
            modifier = Modifier.padding(bottom = 24.dp),
            color = Color.Black, fontWeight = FontWeight.Bold
        )
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) {
        items(viewModel.categoryPlaces) {


            Button(modifier = Modifier
                .padding(2.dp)
                .height(90.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(6.dp),
                onClick = { onCategoryChose(it) }
            ) {
                Column(
                ) {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterHorizontally),
                        tint = colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        it.name,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }

    }
}

@Composable
private fun subCategories(
    category: ArrudeiaCategoryPlace,
    onSubCategoryChoose: (ArrudeiaSubCategoryPlace) -> Unit,
) {


    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.type_of_place),
            modifier = Modifier.padding(bottom = 24.dp),
            color = Color.Black, fontWeight = FontWeight.Bold
        )
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) {

        items(category.subcategories) {

            Button(modifier = Modifier
                .padding(2.dp)
                .height(90.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(6.dp),
                onClick = { onSubCategoryChoose(it) }
            ) {
                Column(
                ) {
                    Text(
                        it.name,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun formCategoriesDetail(
    viewModel: ArrudeiaViewModel,
    category: ArrudeiaCategoryPlace,
    subCategoryChoose: ArrudeiaSubCategoryPlace,
) {
    var avaliableChoose by rememberSaveable { mutableStateOf<ArrudeiaAvailablePlace?>(null) }
    var description by rememberSaveable { mutableStateOf("") }
    var phone  by rememberSaveable { mutableStateOf("") }
    var socialNetwork  by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.tell_us_more_about_the_place),
            modifier = Modifier.padding(bottom = 24.dp),
            color = Color.Black, fontWeight = FontWeight.Bold
        )
    }
    Column(modifier = Modifier.padding(16.dp)) {


        Text(
            category.name,
            color = Color.Black,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            subCategoryChoose.name,
            color = Color.Black,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.size(10.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .clip(CircleShape),
            value = description,
            onValueChange = {
                description = it
            },
            label = { Text(text = stringResource(R.string.description)) },
            minLines = 2,
            maxLines = 6,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White
            ),
            shape = RoundedCornerShape(25.dp),
        )

        Spacer(modifier = Modifier.size(10.dp))


        TextFieldInput(
            hint = stringResource(id = R.string.phone),
            phone,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_smartphone),
            onValueChange = { phone =it },
            KeyboardType.Phone,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(10.dp))


        TextFieldInput(
            hint = stringResource(id = R.string.social_network),
            socialNetwork,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_email),
            onValueChange = { socialNetwork = it },
            KeyboardType.Email,
            ImeAction.Done
        )
    }
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) {
        items(category.available) {


            Button(modifier = Modifier
                .padding(2.dp)
                .height(90.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(6.dp),
                onClick = { avaliableChoose = it }
            ) {
                Column(
                ) {
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        it.name,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun searchAddressInput(
    modifier: Modifier,
    viewModel: ArrudeiaViewModel,
) {
    TextField(
        modifier = modifier,
        value = viewModel.text,
        onValueChange = {
            viewModel.text = it
            viewModel.searchPlaces(it)
        },
        label = { Text(text = stringResource(R.string.address)) },
        minLines = 2,
        maxLines = 2,
        leadingIcon = {
            Icon(
                painter = painterResource(com.arrudeia.core.designsystem.R.drawable.ic_search),
                contentDescription = null,
                tint = Color.Black
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White
        ),
        shape = RoundedCornerShape(25.dp),
    )
}

@Composable
private fun ColumnScope.resultSearchAddress(
    focusInSearchAddress: Boolean,
    viewModel: ArrudeiaViewModel
) {
    AnimatedVisibility(
        focusInSearchAddress,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.locationAutofill) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        viewModel.text = it.address
                        viewModel.locationAutofill.clear()
                        viewModel.getCoordinates(it)
                    }) {
                    Text(it.address, color = Color.Black)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}


private fun locationEnabled(activity: Activity): Boolean {
    val locationManager =
        activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return LocationManagerCompat.isLocationEnabled(locationManager)
}

private fun requestLocationEnable(activity: Activity, viewModel: ArrudeiaViewModel) {
    activity.let {
        val locationRequest = LocationRequest.create()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        LocationServices.getSettingsClient(it).checkLocationSettings(builder.build())
            .addOnSuccessListener {
                if (it.locationSettingsStates?.isLocationPresent == true) {
                    viewModel.getCurrentLocation()
                }
            }.addOnFailureListener {
                if (it is ResolvableApiException) {
                    try {
                        it.startResolutionForResult(activity, 999)
                    } catch (e: IntentSender.SendIntentException) {
                        e.printStackTrace()
                    }
                }
            }

    }
}

private fun hasRequiredPermissions(context: Context): Boolean {
    return MAPS_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            context,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}

class MAPS_UTIL {
    companion object {
        val MAPS_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        const val PERMISSION_REQUEST_CODE = 101
    }
}

@Composable
fun PlaceDetailScreen(modifier: Modifier, place: ArrudeiaPlaceDetails) {
    Surface(
        modifier = modifier,
        color = colorResource(id = com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                Icons.Rounded.Close, null, modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.End),
                tint = colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary)
            )
            Text(
                text = place.name,

                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Tipo: ${place.type}",

                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Avaliação: ${place.rating}",

                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Preço: ${place.priceLevel}",

                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (place.comments?.isNotEmpty() == true) {
                Text(
                    text = "Comentários:",

                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun search(modifier: Modifier, searchTravel: String, onSearchTravelChange: (String) -> Unit) {
    TextField(
        modifier = modifier,
        value = searchTravel,
        onValueChange = onSearchTravelChange,
        label = { Text(text = stringResource(R.string.address)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(com.arrudeia.core.designsystem.R.drawable.ic_search),
                contentDescription = null,
                tint = Color.Black
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White
        ),
        shape = RoundedCornerShape(25.dp),
    )
}

