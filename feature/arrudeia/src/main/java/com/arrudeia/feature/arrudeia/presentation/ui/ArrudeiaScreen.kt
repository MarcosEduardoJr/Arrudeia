package com.arrudeia.feature.arrudeia.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.arrudeia.core.common.BuildConfig
import com.arrudeia.core.designsystem.R.drawable.ic_building_circle
import com.arrudeia.core.designsystem.R.drawable.ic_car_circle
import com.arrudeia.core.designsystem.R.drawable.ic_hotel_circle
import com.arrudeia.core.designsystem.R.drawable.ic_outdoors_circle
import com.arrudeia.core.designsystem.R.drawable.ic_pin
import com.arrudeia.core.designsystem.R.drawable.ic_restaurant_circle
import com.arrudeia.core.designsystem.R.drawable.ic_shop_circle
import com.arrudeia.core.designsystem.R.drawable.ic_surf_person_circle
import com.arrudeia.core.designsystem.R.drawable.ic_train_circle
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.component.TextFieldInput
import com.arrudeia.core.designsystem.component.camera.ImageSelectionScreen
import com.arrudeia.feature.arrudeia.R
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaCategoryPlaceUiModel
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaPlaceDetailsUiModel
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaSubCategoryPlaceUiModel
import com.arrudeia.feature.arrudeia.presentation.viewmodel.ArrudeiaViewModel
import com.arrudeia.feature.arrudeia.presentation.viewmodel.LocationState
import com.arrudeia.feature.arrudeia.presentation.viewmodel.SaveMarkerUiState
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Transition
import com.bumptech.glide.request.target.CustomTarget
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


@Composable
fun arrudeiaRoute(
    onBackClick: () -> Unit,
    viewModel: ArrudeiaViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val context = LocalContext.current
    viewModel.getPlacesMarker()
    viewModel.fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context as Activity)
    Places.initialize(context.applicationContext, BuildConfig.MAPS_API_KEY)
    viewModel.placesClient = Places.createClient(context)
    viewModel.geoCoder = Geocoder(context)
    viewModel.getCurrentLocation()
    locationScreen(
        viewModel = viewModel,
        onShowSnackbar = onShowSnackbar,
        onBackClick = onBackClick
    )

}

@Composable
fun locationScreen(
    modifier: Modifier = Modifier,
    viewModel: ArrudeiaViewModel,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit,
) {
    val activity = LocalContext.current as Activity

    AnimatedContent(
        viewModel.locationState, label = ""
    ) { state ->
        when (state) {

            is LocationState.LocationDisabled -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text(
                            text = stringResource(id = R.string.need_location_continue),
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        ArrudeiaButtonColor(
                            onClick = {
                                requestLocationEnable(activity, viewModel)
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            colorButton = colorResource(com.arrudeia.core.designsystem.R.color.colorPrimary),
                        ) {

                            Text(
                                text = stringResource(R.string.enable_location),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }

                }

            }

            is LocationState.LocationLoading -> {

                val preloaderLottieComposition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(
                        R.raw.animation_arrudeia_loading
                    )
                )

                val preloaderProgress by animateLottieCompositionAsState(
                    preloaderLottieComposition,
                    iterations = LottieConstants.IterateForever,
                    isPlaying = true
                )


                LottieAnimation(
                    composition = preloaderLottieComposition,
                    progress = preloaderProgress,
                    modifier = modifier
                )

            }

            is LocationState.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text(
                            text = stringResource(id = R.string.impossible_get_location),
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        ArrudeiaButtonColor(
                            onClick = {
                                viewModel.getCurrentLocation()
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            colorButton = colorResource(com.arrudeia.core.designsystem.R.color.colorPrimary),
                        ) {

                            Text(
                                text = stringResource(R.string.retry),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            is LocationState.LocationAvailable -> {
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(state.cameraLatLang, CAM_ZOOM)
                }
                var isNavigationStarted by remember { mutableStateOf(false) }
                val context = LocalContext.current
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(
                    context
                )
                var openGoogleMap by remember { mutableStateOf(false) }
                var arrudeia by rememberSaveable { mutableStateOf(false) }
                val mapUiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
                val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
                val scope = rememberCoroutineScope()
                var showMarkers by remember { mutableStateOf(true) }


                LaunchedEffect(viewModel.currentLatLong) {
                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(viewModel.currentLatLong))
                }

                LaunchedEffect(cameraPositionState.isMoving) {
                    if (!cameraPositionState.isMoving) {
                        viewModel.getAddress(cameraPositionState.position.target)
                        if (viewModel.currentLatLong != cameraPositionState.position.target) {
                            arrudeia = true
                            isNavigationStarted = false
                        }
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
                        }
                    ) {

                        viewModel.places.forEach { place ->
                            place.location?.let {
                                var image by remember {
                                    mutableStateOf<Bitmap?>(null)
                                }
                                //    var bmp = if (place.image.isNotEmpty()) {
                                if (place.imageBitmap == null) {
                                    getBitmapFromUrl(context = context, place.image) { bitmap ->
                                        place.imageBitmap = bitmap
                                        image = place.imageBitmap
                                    }
                                } else {
                                    image = place.imageBitmap
                                }
                                image?.let { img ->
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
                                        },
                                        icon = BitmapDescriptorFactory.fromBitmap(img)
                                    )

                                }
                                //      } else {
                                //          getBitmap(
                                //              getMarkerIcon(place.categoryName.name),
                                //             LocalContext.current
                                //       )
                                //  }

                            }
                        }

                        if (isNavigationStarted) {
                            viewModel.currentLocation.value?.let {
                                Marker(
                                    state = rememberMarkerState(
                                        position = LatLng(
                                            it.latitude,
                                            it.longitude
                                        )
                                    ),
                                    draggable = false,
                                    title = stringResource(id = R.string.start),
                                )
                            }
                            viewModel.destinyLocation.value?.let {
                                Marker(
                                    state = rememberMarkerState(
                                        position = LatLng(
                                            it.latitude,
                                            it.longitude
                                        )
                                    ),
                                    draggable = false,
                                    title = stringResource(id = R.string.destiny),
                                )
                            }
                            Polyline(
                                points = viewModel.arrudeiaPolyline
                            )

                        }
                    }
                    Icon(
                        Icons.Rounded.LocationOn, null, modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        tint = colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary)
                    )

                    if (openGoogleMap) {
                        openMap(viewModel)
                        openGoogleMap = false
                    }

                    if (isPlaceClicked != null) {
                        placeDetailScreen(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth(),
                            place = isPlaceClicked!!,
                            isNavigationStarted,
                            fusedLocationClient,
                            viewModel,
                            cameraPositionState,
                            { openGoogleMap = it },
                            { isNavigationStarted = it },
                            { isPlaceClicked = it },
                        )
                    } else {
                        searchAddress(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth(),
                            viewModel,
                            cameraPositionState,
                            onShowSnackbar,
                            isNavigationStartedChange = { isNavigationStarted = it },
                            isNavigationStarted,
                            arrudeiaChange = { arrudeia = it },
                            arrudeia,
                            { openGoogleMap = it }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                    ) {

                        CircularIconButton(
                            onClick = {
                                onBackClick()
                            },
                            icon = Icons.Rounded.ArrowBack,
                            backgroundColor = colorResource(
                                id = com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
                            ),
                            iconSize = 50.dp,
                            modifier = Modifier
                        )


                    }
                }
            }
        }
    }
}

fun getMarkerIcon(categoryName: String): Int {
    return when (categoryName) {
        "CATEGORY_FOOD" -> ic_restaurant_circle
        "CATEGORY_ACCOMMODATION" -> ic_hotel_circle
        "CATEGORY_OUTDOORS" -> ic_outdoors_circle
        "CATEGORY_ENTERTAINMENT" -> ic_surf_person_circle
        "CATEGORY_PURCHASES_OR_SERVICES" -> ic_shop_circle
        "CATEGORY_PUBLIC_SERVICE" -> ic_building_circle
        "CATEGORY_TRANSPORT" -> ic_train_circle
        "CATEGORY_CAR_SERVICES" -> ic_car_circle
        else -> {
            ic_pin
        }
    }
}

@Composable
private fun searchAddress(
    modifier: Modifier,
    viewModel: ArrudeiaViewModel,
    cameraPositionState: CameraPositionState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    isNavigationStartedChange: (Boolean) -> Unit,
    isNavigationStarted: Boolean,
    arrudeiaChange: (Boolean) -> Unit,
    arrudeia: Boolean,
    openGoogleMapChange: (Boolean) -> Unit
) {
    val color =
        colorResource(id = com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9)
    var addMarker by rememberSaveable { mutableStateOf(false) }
    var showCamera by rememberSaveable { mutableStateOf(false) }
    var categoryChose by rememberSaveable { mutableStateOf<ArrudeiaCategoryPlaceUiModel?>(null) }
    var subCategoryChose by rememberSaveable {
        mutableStateOf<ArrudeiaSubCategoryPlaceUiModel?>(
            null
        )
    }
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

            if (addMarker) {
                addMarkerBottomSheet(
                    {
                        addMarker = false
                        viewModel.onTakePhoto(null)
                    },
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

            val titleAddNewPlace = stringResource(id = R.string.add_new_place)
            ExtendedFloatingActionButton(
                text = { Text(text = titleAddNewPlace) },
                icon = { Icon(Icons.Rounded.AddLocationAlt, null) },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 16.dp, bottom = 16.dp),
                shape = CircleShape,
                onClick = {
                    categoryChose = null
                    subCategoryChose = null
                    description = ""
                    phone = ""
                    socialNetwork = ""
                    name = ""
                    addMarker = true
                },
                containerColor = colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary),
                contentColor = Color.White
            )


            Box(
                modifier = Modifier
                    .background(
                        color,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .heightIn(100.dp, 400.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val context = LocalContext.current
                    val keyboardController = LocalSoftwareKeyboardController.current
                    var focusInSearchAddress by remember { mutableStateOf(false) }
                    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(
                        context
                    )

                    if (focusInSearchAddress) {
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.End)
                                .clickable { focusInSearchAddress = false },
                            text = stringResource(R.string.down),
                            color = colorResource(id = com.arrudeia.core.designsystem.R.color.text_grey)
                        )
                    } else {
                        keyboardController?.hide()
                    }


                    searchAddressInput(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically)
                            .clip(CircleShape)
                            .onFocusChanged {
                                focusInSearchAddress = it.isFocused
                            },
                        viewModel, arrudeiaChange = { arrudeiaChange(it) },
                    )
                    if (!arrudeia)
                        resultSearchAddress(
                            focusInSearchAddress,
                            viewModel,
                            arrudeiaChange = { arrudeiaChange(it) },
                            focusInSearchAddressChange = { focusInSearchAddress = it })


                    if (isNavigationStarted) {

                        ArrudeiaButtonColor(
                            onClick = {
                                isNavigationStartedChange(false)
                            },
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, top = 10.dp)
                                .fillMaxWidth(),
                            colorButton = colorResource(com.arrudeia.core.designsystem.R.color.text_grey),
                        ) {

                            Text(
                                text = stringResource(R.string.back),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }


                    if (arrudeia) {
                        ArrudeiaButtonColor(
                            onClick = {
                                if (!isNavigationStarted)
                                    arrudeia(
                                        context,
                                        fusedLocationClient,
                                        viewModel,
                                        cameraPositionState,
                                        isNavigationStartedChange
                                    )
                                else
                                    openGoogleMapChange(true)

                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            colorButton = colorResource(com.arrudeia.core.designsystem.R.color.colorPrimary),
                        ) {
                            Icon(
                                painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_navigation_up),
                                contentDescription = null,
                                tint = Color.White
                            )
                            val textButton =
                                if (isNavigationStarted) stringResource(id = R.string.navigate) else stringResource(
                                    id = R.string.arrudeia
                                )
                            Text(
                                text = textButton,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }

        }
}

@Composable
fun openMap(viewModel: ArrudeiaViewModel) {
    val navigationUri =
        "google.navigation:q=" +
                "${viewModel.destinyLocation.value!!.latitude}," +
                "${viewModel.destinyLocation.value!!.longitude}"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(navigationUri))
    intent.setPackage("com.google.android.apps.maps")
    if (intent.resolveActivity(LocalContext.current.packageManager) != null) {
        LocalContext.current.startActivity(intent)
    }
}

private fun arrudeia(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: ArrudeiaViewModel,
    cameraPositionState: CameraPositionState,
    isNavigationStartedChange: (Boolean) -> Unit
) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location.also {
            viewModel.arrudeia(
                LatLng(it.latitude, it.longitude),
                cameraPositionState.position.target
            )
            isNavigationStartedChange(true)
        }

    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addMarkerBottomSheet(
    onDismissRequest: () -> Unit,
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
                LaunchedEffect(Unit) {
                    scope.launch {
                        sheetState.expand()
                    }
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
        columns = GridCells.Fixed(GRID_CELLS_COUNT),
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
        columns = GridCells.Fixed(GRID_CELLS_COUNT),
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
            colors = ButtonDefaults.buttonColors(
                colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary)
            ),
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
            label = { Text(text = stringResource(R.string.description), color = Color.Black) },
            minLines = 2,
            maxLines = 6,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                cursorColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            shape = RoundedCornerShape(16.dp),
        )
    }
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(GRID_CELLS_COUNT),
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) {
        items(category.available) {
            val buttonColor =
                if (availableChoose.isEmpty() || !availableChoose.contains(it))
                    ButtonDefaults.buttonColors(containerColor = Color.White)
                else
                    ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary)
                    )

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
    arrudeiaChange: (Boolean) -> Unit
) {
    TextField(
        modifier = modifier,
        value = viewModel.text,
        onValueChange = {
            viewModel.text = it
            viewModel.searchPlaces(it)
            arrudeiaChange(false)
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
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            cursorColor = Color.Black,
            unfocusedTextColor = Color.Black,
        ),
        shape = RoundedCornerShape(25.dp),
    )
}

@Composable
private fun ColumnScope.resultSearchAddress(
    focusInSearchAddress: Boolean,
    viewModel: ArrudeiaViewModel,
    arrudeiaChange: (Boolean) -> Unit,
    focusInSearchAddressChange: (Boolean) -> Unit,
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
                        arrudeiaChange(true)
                        focusInSearchAddressChange(false)
                    }) {
                    Text(it.address, color = Color.Black)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}


private fun requestLocationEnable(context: Context, viewModel: ArrudeiaViewModel) {
    ActivityCompat.requestPermissions(
        context as Activity, MAPS_PERMISSIONS, PERMISSION_REQUEST_CODE
    )
    viewModel.getCurrentLocation()
}


val MAPS_PERMISSIONS = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

const val PERMISSION_REQUEST_CODE = 101


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun placeDetailScreen(
    modifier: Modifier,
    place: ArrudeiaPlaceDetailsUiModel,
    isNavigationStarted: Boolean,
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: ArrudeiaViewModel,
    cameraPositionState: CameraPositionState,
    openGoogleMap: (Boolean) -> Unit,
    isNavigationStartedChange: (Boolean) -> Unit,
    isPlaceClickedChange: (ArrudeiaPlaceDetailsUiModel?) -> Unit
) {
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
                    .align(Alignment.End)
                    .clickable {

                        isPlaceClickedChange(null)
                        isNavigationStartedChange(false)
                        openGoogleMap(false)
                    },
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

            ArrudeiaButtonColor(
                onClick = {
                    if (!isNavigationStarted)
                        arrudeia(
                            context,
                            fusedLocationClient,
                            viewModel,
                            cameraPositionState,
                            isNavigationStartedChange
                        )
                    else
                        openGoogleMap(true)

                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colorButton = colorResource(com.arrudeia.core.designsystem.R.color.colorPrimary),
            ) {
                Icon(
                    painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_navigation_up),
                    contentDescription = null,
                    tint = Color.White
                )
                val textButton =
                    if (isNavigationStarted) stringResource(id = R.string.navigate) else stringResource(
                        id = R.string.arrudeia
                    )
                Text(
                    text = textButton,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
        }
    }
}

const val CAM_ZOOM = 15f
const val GRID_CELLS_COUNT = 3
const val BITMAP_WIDTH = 100
const val BITMAP_HEIGHT = 100
private fun getBitmap(drawableRes: Int, context: Context): Bitmap? {
    val bitmapdraw = ContextCompat.getDrawable(context, drawableRes)
    return bitmapdraw?.toBitmap(BITMAP_WIDTH, BITMAP_HEIGHT)
}

fun getBitmapFromUrl(context: Context, url: String, callback: (Bitmap) -> Unit) {
    Glide.with(context)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
            ) {
                callback(resizeBitmap(getRoundedBitmap(resource), BITMAP_WIDTH, BITMAP_HEIGHT))
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // Handle cleanup here if necessary
            }
        })
}


fun resizeBitmap(source: Bitmap, width: Int, height: Int): Bitmap {
    return Bitmap.createScaledBitmap(source, width, height, false)
}

fun getRoundedBitmap(bitmap: Bitmap): Bitmap {
    val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)

    val paint = Paint()
    val rect = Rect(0, 0, bitmap.width, bitmap.height)

    val rectF = RectF(rect)
    val roundPx = bitmap.width.coerceAtMost(bitmap.height) / 2.0f

    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = com.arrudeia.core.designsystem.R.color.colorPrimary
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, rect, rect, paint)

    return output
}