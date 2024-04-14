package com.arrudeia.feature.arrudeia.presentation.ui

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
import androidx.compose.ui.layout.ContentScale
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.TextFieldInput
import com.arrudeia.core.designsystem.component.camera.ImageSelectionScreen
import com.arrudeia.core.network.BuildConfig
import com.arrudeia.feature.arrudeia.R
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaCategoryPlaceUiModel
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaPlaceDetailsUiModel
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaSubCategoryPlaceUiModel
import com.arrudeia.feature.arrudeia.presentation.ui.MAPS_UTIL.Companion.MAPS_PERMISSIONS
import com.arrudeia.feature.arrudeia.presentation.ui.MAPS_UTIL.Companion.PERMISSION_REQUEST_CODE
import com.arrudeia.feature.arrudeia.presentation.viewmodel.ArrudeiaViewModel
import com.arrudeia.feature.arrudeia.presentation.viewmodel.LocationState
import com.arrudeia.feature.arrudeia.presentation.viewmodel.SaveMarkerUiState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
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
        viewModel.getPlacesMarker()
        viewModel.fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context as Activity)
        Places.initialize(context.applicationContext, BuildConfig.MAPS_API_KEY)
        viewModel.placesClient = Places.createClient(context)
        viewModel.geoCoder = Geocoder(context)
        LocationScreen(viewModel = viewModel, onShowSnackbar = onShowSnackbar)
    }
}

@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    viewModel: ArrudeiaViewModel,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
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

                var isPlaceClicked by rememberSaveable {
                    mutableStateOf<ArrudeiaPlaceDetailsUiModel?>(
                        null
                    )
                }



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
                            place.location?.let {
                                Marker(
                                    state = rememberMarkerState(
                                        position = LatLng(
                                            it.latitude,
                                            it.longitude
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
                                .fillMaxWidth(), viewModel, cameraPositionState, onShowSnackbar
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
    cameraPositionState: CameraPositionState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val color = colorResource(id = com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9)
    var addMarker by rememberSaveable { mutableStateOf(false) }
    var saveMarker by rememberSaveable { mutableStateOf(false) }
    var showCamera by rememberSaveable { mutableStateOf(false) }
    var categoryChose by rememberSaveable { mutableStateOf<ArrudeiaCategoryPlaceUiModel?>(null) }
    var subCategoryChose by rememberSaveable { mutableStateOf<ArrudeiaSubCategoryPlaceUiModel?>(null) }
    var description by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var socialNetwork by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }


    if (showCamera)
        ImageSelectionScreen(
            { viewModel.onTakePhoto(it) },
            { showCamera = it }
        )
    else
        Column(
            modifier = modifier,
        ) {

            if (addMarker){
                AddMarkerBottomSheet(
                    {
                        addMarker = false
                        viewModel.onTakePhoto(null)
                    },
                    { saveMarker = true },
                    viewModel,
                    showCameraChange = { showCamera = it },
                    categoryPlaceChange = { categoryChose = it },
                    subCategoryPlaceChange = { subCategoryChose = it },
                    categoryChose,
                    subCategoryChose,
                    name,
                    { name = it },
                    phone,
                    { phone = it },
                    socialNetwork,
                    { socialNetwork = it },
                    description,
                    { description = it },
                    cameraPositionState,
                    onShowSnackbar
                )
        }

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
fun AddMarkerBottomSheet(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    viewModel: ArrudeiaViewModel,
    showCameraChange: (Boolean) -> Unit,
    categoryPlaceChange: (ArrudeiaCategoryPlaceUiModel?) -> Unit,
    subCategoryPlaceChange: (ArrudeiaSubCategoryPlaceUiModel?) -> Unit,
    categoryPlace: ArrudeiaCategoryPlaceUiModel?,
    subCategoryPlace: ArrudeiaSubCategoryPlaceUiModel?,
    name: String?,
    onNameChange: (String) -> Unit,
    phone: String?,
    onPhoneChange: (String) -> Unit,
    socialNetwork: String?,
    onSocialNetworkChange: (String) -> Unit,
    description: String?,
    onDescriptionChange: (String) -> Unit,
    cameraPositionState: CameraPositionState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val saveMarkerSharedFlow by viewModel.saveMarkerSharedFlow.collectAsStateWithLifecycle()
    var saving by rememberSaveable { mutableStateOf(false) }
    ModalBottomSheet(
        containerColor = colorResource(id = com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {

        if (saving)
            when (saveMarkerSharedFlow) {
                is SaveMarkerUiState.Success -> {
                    saving = false
                    onDismissRequest()
                }

                is SaveMarkerUiState.Error -> {
                    saving = false
                    val message =
                        stringResource((saveMarkerSharedFlow as SaveMarkerUiState.Error).message)
                    LaunchedEffect(Unit) {
                        scope.launch {
                            onShowSnackbar(
                                message,
                                ""
                            )
                        }
                    }
                    onDismissRequest()
                }

                is SaveMarkerUiState.Loading -> {
                    ArrudeiaLoadingWheel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                    )
                }

            }
        var title =
            if (subCategoryPlace != null) subCategoryPlace?.category?.getString(context)
                .orEmpty() else if (categoryPlace == null) stringResource(
                id = R.string.category_of_place
            ) else stringResource(id = R.string.type_of_place)

        Box(modifier = Modifier.fillMaxWidth()) {
            if (categoryPlace != null && subCategoryPlace != null) {
                sheetState
                TextButton(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(Alignment.CenterStart),
                    onClick = {
                        if (!saving)
                            viewModel.savePlace(
                                name.orEmpty(),
                                phone.orEmpty(),
                                socialNetwork.orEmpty(),
                                description.orEmpty(),
                                categoryPlace.category.name,
                                subCategoryPlace.category.name,
                                cameraPositionState.position.target
                            )
                        saving = true
                    },
                ) {
                    Text(
                        stringResource(R.string.save),
                        color = colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary)
                    )
                }
                scope.launch {
                    sheetState.expand()
                }
            }

            Text(
                text = title,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.Center),
                color = Color.Black, fontWeight = FontWeight.Bold
            )

            TextButton(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterEnd),
                onClick = {
                    if (!saving) {
                        if (categoryPlace == null) onDismissRequest()
                        else {
                            categoryPlaceChange(null)
                            subCategoryPlaceChange(null)
                        }
                    }
                },
            ) {
                Text(
                    stringResource(R.string.cancel),
                    color = colorResource(id = com.arrudeia.core.designsystem.R.color.text_grey)
                )
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {


            if (subCategoryPlace != null && categoryPlace != null)
                categoryPlace?.let { cat ->
                    subCategoryPlace?.let { sub ->
                        formCategoriesDetail(
                            viewModel, cat,
                            sub,
                            showCamera = { showCameraChange(it) },
                            onNameChange = onNameChange,
                            name.orEmpty(),
                            onPhoneChange = onPhoneChange,
                            phone.orEmpty(),
                            onSocialNetworkChange = onSocialNetworkChange,
                            socialNetwork.orEmpty(),
                            onDescriptionChange = onDescriptionChange,
                            description.orEmpty(),
                        )
                    }
                }
            else if (categoryPlace == null)
                categories(viewModel, onCategoryChose = { categoryPlaceChange(it) })
            else
                categoryPlace?.let { category ->
                    subCategories(
                        category,
                        onSubCategoryChoose = { subCategoryPlaceChange(it) })
                }

        }

    }

}

@Composable
private fun categories(
    viewModel: ArrudeiaViewModel,
    onCategoryChose: (ArrudeiaCategoryPlaceUiModel?) -> Unit,
) {

    val context = LocalContext.current

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
                        it.category.getString(context),
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
    category: ArrudeiaCategoryPlaceUiModel,
    onSubCategoryChoose: (ArrudeiaSubCategoryPlaceUiModel) -> Unit,
) {

    val context = LocalContext.current
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
                        it.category.getString(context),
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun formCategoriesDetail(
    viewModel: ArrudeiaViewModel,
    category: ArrudeiaCategoryPlaceUiModel,
    subCategoryChoose: ArrudeiaSubCategoryPlaceUiModel,
    showCamera: (Boolean) -> Unit,
    onNameChange: (String) -> Unit,
    name: String,
    onPhoneChange: (String) -> Unit,
    phone: String,
    onSocialNetworkChange: (String) -> Unit,
    socialNetwork: String,
    onDescriptionChange: (String) -> Unit,
    description: String,
) {
    val availableChoose = viewModel.availables
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {


        Spacer(modifier = Modifier.size(10.dp))


        TextFieldInput(
            hint = stringResource(id = R.string.name),
            name,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_pin),
            onValueChange = onNameChange,
            KeyboardType.Text,
            ImeAction.Next
        )


        Spacer(modifier = Modifier.size(10.dp))


        TextFieldInput(
            hint = stringResource(id = R.string.phone),
            phone,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_smartphone),
            onValueChange = onPhoneChange,
            KeyboardType.Phone,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(10.dp))


        TextFieldInput(
            hint = stringResource(id = R.string.social_network),
            socialNetwork,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_email),
            onValueChange = onSocialNetworkChange,
            KeyboardType.Email,
            ImeAction.Done
        )

        Spacer(modifier = Modifier.size(20.dp))

        TextButton(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary)),
            onClick = {
                showCamera(true)
            },
        ) {
            Text(
                stringResource(R.string.shoot_image),
                color = Color.White
            )
        }

        if (viewModel.uri.value != null)
            GlideImage(
                model = viewModel.uri.value,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .padding(top = 20.dp)
            )

        Spacer(modifier = Modifier.size(20.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically),
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(text = stringResource(R.string.description)) },
            minLines = 2,
            maxLines = 6,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
        )
    }
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) {
        items(category.available) {
            val buttonColor =
                if (availableChoose.isEmpty() || !availableChoose.contains(it))
                    ButtonDefaults.buttonColors(containerColor = Color.White)
                else
                    ButtonDefaults.buttonColors(containerColor = colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary))

            Button(modifier = Modifier
                .padding(2.dp)
                .height(90.dp),
                colors = buttonColor,
                shape = RoundedCornerShape(6.dp),
                onClick = {
                    if (!availableChoose.contains(it)) viewModel.addAvailables(it) else viewModel.removeAvailables(
                        it
                    )
                }
            ) {
                Column(
                ) {
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        it.available.getString(context),
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlaceDetailScreen(modifier: Modifier, place: ArrudeiaPlaceDetailsUiModel) {
    val context = LocalContext.current
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
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Text(
                text = place.subCategoryName.getString(context),
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
            )

            if (place.image.isNotEmpty())
                GlideImage(
                    model = place.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(180.dp)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                )


            var contact = ""
            if (place.phone.isNotEmpty())
                contact = place.phone

            if (place.socialNetwork.isNotEmpty())
                contact += " | " + "@${place.socialNetwork}"

            if (contact.isNotEmpty())
                Text(
                    text = contact,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )


            if (place.description.isNotEmpty())
                Text(
                    text = place.description,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

            if (place.available?.isNotEmpty() == true) {
                Text(
                    text = stringResource(id = R.string.services),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyColumn(content = {
                    items(place.available) { itemAvailable ->
                        Text(
                            text = AvailableOptions.getStringFromEnum(
                                context,
                                itemAvailable.available
                            )
                                .orEmpty(),
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                })

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

