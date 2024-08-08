package com.arrudeia.feature.home.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.feature.home.presentation.model.TravelUIModel
import com.arrudeia.feature.home.presentation.navigation.param.PlaceDetailParam
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import java.util.Locale


@Composable
internal fun homeRoute(
    onRouteClick: (String) -> Unit,
    onStoriesClick: (String) -> Unit,
    onTripDetailClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    showBottomBar: (Boolean) -> Unit,
    onNewPlaceClick: (String) -> Unit,
    onPlaceDetailsClick: (PlaceDetailParam) -> Unit
) {
    showBottomBar(true)
    val viewModel: HomeViewModel = hiltViewModel()
    viewModel.getUserPersonalInformation()
    homeView(
        onRouteClick,
        viewModel,
        onStoriesClick,
        onTripDetailClick,
        onShowSnackbar,
        onNewPlaceClick,
        onPlaceDetailsClick
    )
}

@Composable
fun homeView(
    onRouteClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onStoriesClick: (String) -> Unit,
    onTripDetailClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onNewPlaceClick: (String) -> Unit,
    onPlaceDetailsClick: (PlaceDetailParam) -> Unit
) {
    var searchTravel by rememberSaveable { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = background_grey_F7F7F9))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .align(Alignment.TopCenter)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {

                Spacer(modifier = Modifier.size(10.dp))

                header(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    onShowSnackbar,
                    onRouteClick,
                    viewModel
                )

                Spacer(modifier = Modifier.size(10.dp))

                PagerHome(
                    viewModel, searchTravel, onTripDetailClick,
                    searchChange = { searchTravel = it },
                    onStoriesClick = onStoriesClick,
                    onNewPlaceClick,
                    onPlaceDetailsClick = onPlaceDetailsClick
                )
            }
        }
    }
}

fun filterSearchList(searchTravel: String, list: List<TravelUIModel>): List<TravelUIModel> {
    return list.filter {
        it.name.lowercase(Locale.getDefault()).contains(searchTravel.lowercase(Locale.getDefault()))
                || it.city.lowercase(Locale.getDefault())
            .contains(searchTravel.lowercase(Locale.getDefault()))
    }
}