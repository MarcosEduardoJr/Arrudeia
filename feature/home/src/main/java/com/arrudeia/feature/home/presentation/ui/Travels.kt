package com.arrudeia.feature.home.presentation.ui

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.data.navigation.arrudeiaRoute
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.R.drawable.ic_calendar
import com.arrudeia.core.designsystem.R.drawable.ic_pin
import com.arrudeia.core.designsystem.R.drawable.paid_24px
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.TextSwitch
import com.arrudeia.core.domain.util.toCurrencyReal
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaPlaceDetailsUiModel
import com.arrudeia.feature.home.R.string.places
import com.arrudeia.feature.home.R.string.register_new_place
import com.arrudeia.feature.home.R.string.trips
import com.arrudeia.feature.home.presentation.model.TravelUIModel
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import com.arrudeia.feature.home.presentation.viewmodel.TravelUiState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import kotlinx.coroutines.DelicateCoroutinesApi
import com.arrudeia.feature.home.R.string.empty_list_places
import com.arrudeia.feature.home.presentation.navigation.param.PlaceDetailParam

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun PagerHome(
    viewModel: HomeViewModel,
    search: String,
    onTripDetailClick: (String) -> Unit,
    searchChange: (String) -> Unit,
    onStoriesClick: (String) -> Unit,
    onNewPlaceClick: (String) -> Unit,
    onPlaceDetailsClick: (PlaceDetailParam) -> Unit
) {
    val pages = listOf(
        stringResource(id = places),
        stringResource(id = trips),
    )
    var pagerState = rememberPagerState(initialPage = 0) { pages.size }
    var selectedTab by rememberSaveable { mutableIntStateOf(pagerState.currentPage) }


    LaunchedEffect(selectedTab) {
        pagerState.scrollToPage(selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTab = pagerState.currentPage
        searchChange("")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = background_grey_F7F7F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = background_grey_F7F7F9)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(10.dp))

            TextSwitch(
                modifier = Modifier
                    .height(56.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White)
                    .padding(8.dp),
                selectedIndex = selectedTab,
                items = pages,
                onSelectionChange = {
                    selectedTab = it
                }
            )

            Spacer(modifier = Modifier.size(16.dp))

            search(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .clip(CircleShape),
                search,
                onSearchTravelChange = searchChange,
            )


            Spacer(modifier = Modifier.size(18.dp))
            HorizontalPager(state = pagerState) { currentPage ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (currentPage) {
                        0 -> {

                            PlacesScreen(
                                viewModel,
                                search,
                                onClick = onNewPlaceClick,
                                onPlaceDetailsClick = onPlaceDetailsClick
                            )

                        }

                        1 -> {
                            arrudeiaTv(
                                viewModel, onStoriesClick,
                                Modifier
                                    .fillMaxWidth()
                                    .height(74.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .verticalScroll(rememberScrollState())
                            )
                            tripsScreen(viewModel, search, onTripDetailClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun tripsScreen(
    viewModel: HomeViewModel,
    searchTravel: String,
    onTripDetailClick: (String) -> Unit
) {
    val uiState by viewModel.travelSharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchDataTravels()
    when (uiState) {
        is TravelUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
            )
        }

        is TravelUiState.Error -> {
            Text(
                text = stringResource((uiState as TravelUiState.Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is TravelUiState.Success -> {
            var list = (uiState as TravelUiState.Success).list
            list = filterSearchList(searchTravel, list)
            LazyColumn() {
                items(items = list.toList(), itemContent = {
                    Spacer(modifier = Modifier.size(8.dp))
                    travelItem(
                        it,
                        Modifier.clickable { onTripDetailClick(it.id.toString()) })
                })
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

        else -> {}
    }
}

@Composable
fun PlacesScreen(
    viewModel: HomeViewModel,
    search: String,
    onPlaceDetailsClick: (PlaceDetailParam) -> Unit,
    onClick: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        if (viewModel.places.isNotEmpty()) {

            val filter = if (search.isNotEmpty()) viewModel.places.toList()
                .filter {
                    it.name.lowercase().contains(search.lowercase())
                } else viewModel.places.toList()
            val GRID_CELLS_COUNT = 2
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .clipToBounds(),
                columns = GridCells.Fixed(GRID_CELLS_COUNT),
                contentPadding = PaddingValues(horizontal = 4.dp),
            ) {
                items(filter) {

                    Spacer(modifier = Modifier.size(4.dp))
                    BoxItem(
                        it,
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                val param = PlaceDetailParam()
                                param.setParam(it)
                                onPlaceDetailsClick(param)
                            })
                }

            }
            Spacer(modifier = Modifier.height(40.dp))
        }
        /* LazyColumn() {
              items(items = viewModel.places.toList(), itemContent = {
                  Spacer(modifier = Modifier.size(8.dp))
                  PlacesItem(
                      it,
                      Modifier//.clickable { onTripDetailClick(it.id.toString()
                  )
              })
          }*/
        else
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = empty_list_places),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        Spacer(modifier = Modifier.height(40.dp))
        val titleAddNewPlace = stringResource(id = register_new_place)
        ExtendedFloatingActionButton(
            text = { Text(text = titleAddNewPlace) },
            icon = { Icon(Icons.Rounded.Add, null) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp),
            shape = CircleShape,
            onClick = {
                onClick(arrudeiaRoute)
            },
            containerColor = colorResource(id = colorPrimary),
            contentColor = Color.White
        )
    }
}


@Composable
private fun BoxItem(item: ArrudeiaPlaceDetailsUiModel, modifier: Modifier) {

    Column() {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                BoxImage(item.image)
                BoxContent(
                    item, Modifier.padding(
                        top = 6.dp
                    )
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun BoxImage(item: String?) {
    Card(
        shape = RoundedCornerShape(12.dp)
    ) {
        GlideImage(
            loading = placeholder(
                ColorDrawable(
                    colorResource(
                        id = background_grey_F7F7F9
                    ).toArgb()
                )
            ),
            model = item.orEmpty(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(100.dp),
            contentScale = ContentScale.Crop,
        )
    }
}


@Composable
private fun BoxContent(item: ArrudeiaPlaceDetailsUiModel, modifier: Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = item?.name.orEmpty(),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black
        )

        Text(
            item.subCategoryName.getString(context),
            fontSize = 11.sp,
            color = Color.Black,
            modifier = Modifier.padding(end = 2.dp)
        )


        Row(modifier = Modifier) {
            val priceBadge = if (item.priceLevel != null) item.priceLevel else 0
            for (i in 1 until 4) {
                Image(
                    painter = painterResource(R.drawable.paid_24px),
                    contentDescription = null,
                    colorFilter = if (i <= priceBadge!!) ColorFilter.tint(colorResource(id = colorPrimary)) else ColorFilter.tint(
                        Color.Gray
                    ),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(18.dp)
                )


            }
        }
    }
}


@Composable
fun travelItem(item: TravelUIModel, modifier: Modifier) {

    Column() {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                image(item)
                content(
                    item, Modifier
                        .weight(1f)
                        .padding(2.dp)
                )

            }
        }
    }
}



@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun image(item: TravelUIModel) {
    Card(
        shape = RoundedCornerShape(50.dp)
    ) {
        GlideImage(
            loading = placeholder(
                ColorDrawable(
                    colorResource(
                        id = background_grey_F7F7F9
                    ).toArgb()
                )
            ),
            model = item.coverImageUrl,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun button(item: TravelUIModel, modifier: Modifier) {
    SuggestionChip(
        modifier = modifier,
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = colorResource(
                colorPrimary
            )
        ),
        border = SuggestionChipDefaults.suggestionChipBorder(
            borderColor = Color.Transparent,
            enabled = true
        ),
        onClick = { },
        label = {
            Text(item.price.toCurrencyReal(), fontSize = 11.sp, color = Color.White)
        }
    )
}




@Composable
private fun content(item: TravelUIModel, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = item.name,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 4.dp, end = 2.dp)
        )



        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(ic_pin),
                contentDescription = null,
                modifier = Modifier.padding(start = 4.dp, end = 2.dp)
            )
            Text(
                text = item.shortLocation(),
                fontSize = 12.sp,
                color = Color.Gray
            )

            Image(
                painter = painterResource(ic_calendar),
                contentDescription = null,
                modifier = Modifier.padding(start = 6.dp, end = 2.dp)
            )
            Text(
                text = item.date(),
                fontSize = 12.sp,
                color = Color.Gray
            )


        }

        button(
            item, Modifier
                .height(14.dp)
                .padding(start = 6.dp)
        )

    }


}
