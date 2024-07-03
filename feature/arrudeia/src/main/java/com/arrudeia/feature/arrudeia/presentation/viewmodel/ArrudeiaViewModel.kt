package com.arrudeia.feature.arrudeia.presentation.viewmodel

import android.annotation.SuppressLint
import android.location.Geocoder
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.common.BuildConfig
import com.arrudeia.core.result.Result
import com.arrudeia.feature.arrudeia.domain.GetAllArrudeiaPlacesUseCase
import com.arrudeia.feature.arrudeia.domain.SaveArrudeiaPlaceUseCase
import com.arrudeia.feature.arrudeia.domain.entity.ArrudeiaPlaceDetailsUseCaseEntity
import com.arrudeia.feature.arrudeia.presentation.mock.categoriesPlace
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaAvailablePlaceUiModel
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaCategoryPlaceUiModel
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaPlaceDetailsUiModel
import com.arrudeia.feature.arrudeia.presentation.ui.AvailableOptions
import com.arrudeia.feature.arrudeia.presentation.ui.CategoryOptions
import com.arrudeia.feature.arrudeia.presentation.ui.SubCategoryOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.android.PolyUtil
import com.google.maps.model.TravelMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.concurrent.TimeUnit
import javax.inject.Inject

sealed class LocationState {
    object LocationDisabled : LocationState()
    object LocationLoading : LocationState()
    data class LocationAvailable(val cameraLatLang: LatLng) : LocationState()
    object Error : LocationState()
}

data class AutocompleteResult(
    val address: String,
    val placeId: String,
)

@HiltViewModel
class ArrudeiaViewModel @Inject constructor(
    private val saveArrudeiaPlaceUseCase: SaveArrudeiaPlaceUseCase,
    private val getAllArrudeiaPlacesUseCase: GetAllArrudeiaPlacesUseCase
) : ViewModel() {
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var placesClient: PlacesClient
    lateinit var geoCoder: Geocoder

    var locationState by mutableStateOf<LocationState>(LocationState.LocationLoading)
    val locationAutofill = mutableStateListOf<AutocompleteResult>()

    var currentLatLong by mutableStateOf(LatLng(0.0, 0.0))

    var categoryPlaces: List<ArrudeiaCategoryPlaceUiModel> = categoriesPlace()

    private var job: Job? = null

    fun searchPlaces(query: String) {
        job?.cancel()
        locationAutofill.clear()
        job = viewModelScope.launch {
            val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()
            placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
                locationAutofill += response.autocompletePredictions.map {
                    AutocompleteResult(
                        it.getFullText(null).toString(), it.placeId
                    )
                }
            }.addOnFailureListener {
                it.printStackTrace()
                println(it.cause)
                println(it.message)
            }
        }
    }

    fun getCoordinates(result: AutocompleteResult) {
        val placeFields = listOf(Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(result.placeId, placeFields)
        placesClient.fetchPlace(request).addOnSuccessListener {
            if (it != null) {
                currentLatLong = it.place.latLng!!
            }
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }

    fun getCoordinatesSaveMarker(result: AutocompleteResult, place: ArrudeiaPlaceDetailsUiModel) {
        val placeFields = listOf(Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(result.placeId, placeFields)
        placesClient.fetchPlace(request).addOnSuccessListener {
            if (it != null) {
                places.add(place)
            }
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        locationState = LocationState.LocationLoading
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                locationState =
                    if (location == null && locationState !is LocationState.LocationAvailable) {
                        LocationState.Error
                    } else {
                        currentLatLong = LatLng(location.latitude, location.longitude)
                        LocationState.LocationAvailable(
                            LatLng(
                                location.latitude,
                                location.longitude
                            )
                        )
                    }
            }.addOnFailureListener {
                locationState = LocationState.LocationDisabled
            }
    }

    var text by mutableStateOf("")

    fun getAddress(latLng: LatLng) {
        viewModelScope.launch {
            val address = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            text = address?.get(0)?.getAddressLine(0).orEmpty()
        }
    }

    val places = mutableStateListOf<ArrudeiaPlaceDetailsUiModel>()

    fun getPlacesMarker() {
        viewModelScope.launch {
           when(val result =   getAllArrudeiaPlacesUseCase())
           {
               is Result.Success -> {
                   result.data.toEntity()?.map {
                       places.add(it)
                   }
               }
               else ->{}
           }
        }
    }

    private fun List<ArrudeiaPlaceDetailsUseCaseEntity>?.toEntity():
            MutableList<ArrudeiaPlaceDetailsUiModel>? = if (this == null) null
        else {
            val list = mutableListOf<ArrudeiaPlaceDetailsUiModel>()
            this.let { place ->
                place.forEach { item ->
                    item.let {
                        val listAvaliable = mutableListOf<ArrudeiaAvailablePlaceUiModel>()
                        item.available?.forEach { itemAvaliable ->
                            listAvaliable.add(
                                ArrudeiaAvailablePlaceUiModel(
                                    AvailableOptions.valueOf(itemAvaliable.name)
                                )
                            )
                        }

                        list.add(
                            ArrudeiaPlaceDetailsUiModel(
                                available = listAvaliable,
                                categoryName = CategoryOptions.valueOf(item.categoryName.orEmpty()),
                                description = item.description.orEmpty(),
                                image = item.image.orEmpty(),
                                location = it.location,
                                name = item.name.orEmpty(),
                                phone = item.phone.orEmpty(),
                                priceLevel = item.priceLevel,
                                rating = item.rating,
                                socialNetwork = item.socialNetwork.orEmpty(),
                                subCategoryName = SubCategoryOptions.valueOf(item.subCategoryName.orEmpty()),
                                uuid = item.uuid.orEmpty(), imageBitmap =  null
                            )
                        )
                    }
                }
            }
            list
        }



    fun addPlace(place: ArrudeiaPlaceDetailsUiModel) {
        places.add(place)
    }

    private val _uri = MutableLiveData<Uri?>()
    val uri: LiveData<Uri?> = _uri

    fun onTakePhoto(uri: Uri?) {
        _uri.value = uri
    }


    private val _availables = mutableStateListOf<ArrudeiaAvailablePlaceUiModel>()
    val availables: List<ArrudeiaAvailablePlaceUiModel> = _availables

    fun addAvailables(available: ArrudeiaAvailablePlaceUiModel) {
        _availables.add(available)
    }

    fun removeAvailables(available: ArrudeiaAvailablePlaceUiModel) {
        _availables.remove(available)
    }

    fun savePlace(
        name: String,
        phone: String,
        socialNetwork: String,
        description: String,
        categoryName: String,
        subCategoryName: String,
        target: LatLng
    ) {
        saveMarkerUiState.value = SaveMarkerUiState.Loading
        viewModelScope.launch {
            val result = saveArrudeiaPlaceUseCase(
                name,
                phone,
                socialNetwork,
                description,
                uri.value,
                availables,
                categoryName,
                subCategoryName,
                location = target
            )

            when(result){
                is Result.Success -> {
                    addPlace(
                        ArrudeiaPlaceDetailsUiModel(
                            name = name,
                            phone = phone,
                            socialNetwork = socialNetwork,
                            description = description,
                            categoryName = CategoryOptions.valueOf(categoryName),
                            subCategoryName = SubCategoryOptions.valueOf(subCategoryName),
                            location = target,
                            available = availables, imageBitmap = null
                        )
                    )
                    saveMarkerUiState.value = SaveMarkerUiState.Success(
                        true
                    )
                }
                else ->{
                    saveMarkerUiState.value = SaveMarkerUiState.Error(
                    com.arrudeia.feature.arrudeia.R.string.not_possible_save)
                }
            }
        }
    }


    val currentLocation = mutableStateOf<LatLng?>(null)
    val destinyLocation = mutableStateOf<LatLng?>(null)
    lateinit var arrudeiaPolyline: List<LatLng>
    fun arrudeia(current: LatLng, destination: LatLng) {
        currentLocation.value = current
        destinyLocation.value = destination
        val now = Instant.now()
        val result = DirectionsApi
            .newRequest(getGeoContext())
            .mode(TravelMode.DRIVING)
            .origin("${current.latitude},${current.longitude}")
            .destination("${destination.latitude},${destination.longitude}")
            .departureTime(now).await()
        arrudeiaPolyline = PolyUtil.decode(result.routes[0].overviewPolyline.encodedPath)

    }

    private fun getGeoContext() = GeoApiContext.Builder()
        .apiKey(BuildConfig.MAPS_API_KEY)
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    var saveMarkerUiState: MutableStateFlow<SaveMarkerUiState> =
        MutableStateFlow(SaveMarkerUiState.Loading)
    val saveMarkerSharedFlow = saveMarkerUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SaveMarkerUiState.Loading
    )
}


data class MapViewState(
    val currentLocation: LatLng?,
    val zoomLevel: Float
)


sealed interface SaveMarkerUiState {
    data class Success(val success: Boolean) : SaveMarkerUiState
    data class Error(val message: Int) : SaveMarkerUiState
    data object Loading : SaveMarkerUiState
}
