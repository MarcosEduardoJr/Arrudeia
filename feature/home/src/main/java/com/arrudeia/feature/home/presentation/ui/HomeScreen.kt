package com.arrudeia.feature.home.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.feature.home.presentation.model.TravelUIModel
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import java.util.Locale


@Composable
internal fun homeRoute(
    onRouteClick: (String) -> Unit,
    onStoriesClick: (String) -> Unit,
    onTripDetailClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    showBottomBar: (Boolean) -> Unit,
) {
    showBottomBar(true)
    val viewModel: HomeViewModel = hiltViewModel()
    viewModel.getUserPersonalInformation()
    homeView(onRouteClick, viewModel, onStoriesClick, onTripDetailClick, onShowSnackbar)
}

@Composable
fun homeView(
    onRouteClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onStoriesClick: (String) -> Unit,
    onTripDetailClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
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
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .align(Alignment.TopCenter)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {

                Spacer(modifier = Modifier.size(30.dp))

                header(modifier = Modifier.fillMaxWidth(), onShowSnackbar, onRouteClick, viewModel)

                Spacer(modifier = Modifier.size(30.dp))

                search(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .clip(CircleShape),
                    searchTravel,
                    onSearchTravelChange = { searchTravel = it },
                )

                arrudeiaTv(
                    viewModel, onStoriesClick,
                    Modifier
                        .fillMaxWidth()
                        .height(74.dp)
                        .align(CenterHorizontally)
                        .verticalScroll(rememberScrollState())
                )
                travels(
                    viewModel, searchTravel, onTripDetailClick, Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(
                            CenterHorizontally
                        )
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