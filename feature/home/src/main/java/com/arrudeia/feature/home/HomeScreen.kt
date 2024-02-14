package com.arrudeia.feature.home


import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults.suggestionChipBorder
import androidx.compose.material3.SuggestionChipDefaults.suggestionChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.R.drawable.ic_arrow_down
import com.arrudeia.core.designsystem.R.drawable.ic_calendar
import com.arrudeia.core.designsystem.R.drawable.ic_notification_on
import com.arrudeia.core.designsystem.R.drawable.ic_pin
import com.arrudeia.core.designsystem.R.drawable.ic_preview_profile_image
import com.arrudeia.core.designsystem.R.drawable.ic_search
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.feature.home.R.string.arrudeia_tv
import com.arrudeia.feature.home.R.string.destiny
import com.arrudeia.feature.home.R.string.near_to_you
import com.arrudeia.feature.home.model.ArrudeiaTvUIModel
import com.arrudeia.feature.home.model.TravelUIModel
import com.arrudeia.util.toCurrencyReal
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import java.util.Locale



@Composable
internal fun HomeRoute(
    onRouteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onStoriesClick: (String) -> Unit,
    onTripDetailClick: (String) -> Unit,
) {
    ListItemView(onRouteClick, viewModel, onStoriesClick, onTripDetailClick)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemView(
    onRouteClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onStoriesClick: (String) -> Unit,
    onTripDetailClick: (String) -> Unit,
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

                search(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .clip(CircleShape),
                    searchTravel,
                    onSearchTravelChange = { searchTravel = it }
                )

                Spacer(modifier = Modifier.size(30.dp))
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = stringResource(arrudeia_tv),
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(74.dp)
                        .align(Alignment.CenterHorizontally)
                        .verticalScroll(rememberScrollState())
                ) {

                    val arrTvUiState by viewModel.arrTvSharedFlow.collectAsStateWithLifecycle()
                    viewModel.fetchDataArrTv()
                    when (arrTvUiState) {
                        is ArrudeiaTvUiState.Loading -> {
                            ArrudeiaLoadingWheel(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                contentDesc = stringResource(id = R.string.loading),
                            )
                        }

                        is ArrudeiaTvUiState.Error -> {
                            Text(
                                text = stringResource((arrTvUiState as ArrudeiaTvUiState.Error).message),
                                modifier = Modifier.padding(4.dp)
                            )
                        }

                        is ArrudeiaTvUiState.Success -> {

                            val list = (arrTvUiState as ArrudeiaTvUiState.Success).list
                            LazyRow(modifier = Modifier.fillMaxHeight()) {
                                items(items = list.toList(), itemContent = {
                                    Spacer(modifier = Modifier.size(8.dp))
                                    arrudeiaTvItem(
                                        Modifier
                                            .align(Alignment.Center)
                                            .clickable { onStoriesClick(it.id.toString()) }, it
                                    )
                                })
                            }
                        }

                        else -> {
                            Toast.makeText(
                                LocalContext.current,
                                arrTvUiState.toString(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

                }

                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = stringResource(near_to_you),
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )

                val uiState by viewModel.travelSharedFlow.collectAsStateWithLifecycle()
                viewModel.fetchDataTravels()
                when (uiState) {
                    is TravelUiState.Loading -> {
                        ArrudeiaLoadingWheel(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .align(
                                    CenterHorizontally
                                ),
                            contentDesc = stringResource(id = R.string.loading),
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
                                    onRouteClick,
                                    it,
                                    Modifier.clickable { onTripDetailClick(it.id.toString()) })
                            })
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                    }

                    else -> {
                        Toast.makeText(LocalContext.current, uiState.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun search(modifier: Modifier, searchTravel: String, onSearchTravelChange: (String) -> Unit) {
    TextField(
        modifier = modifier,
        value = searchTravel,
        onValueChange = onSearchTravelChange,
        label = { Text(text = stringResource(destiny)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(ic_search),
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
fun header(modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        avatar(
            modifier = Modifier
                .size(37.dp)
                .align(Alignment.CenterStart)
        )

        cityDrop(modifier = Modifier.align(Alignment.Center))

        notification(
            modifier = Modifier
                .size(37.dp)
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun cityDrop(modifier: Modifier) {
    Row(modifier = modifier) {
        Icon(
            painter = painterResource(id = ic_pin),
            contentDescription = null,
            modifier = Modifier.align(
                Alignment.CenterVertically
            ),
            tint = colorResource(id = colorPrimary)
        )
        Text(text = "Recife, PE", modifier = Modifier.padding(4.dp))
        Icon(
            painter = painterResource(id = ic_arrow_down),
            contentDescription = null,
            modifier = Modifier
                .align(
                    Alignment.CenterVertically
                )
                .size(12.dp),
            tint = colorResource(id = colorPrimary)
        )
    }
}

@Composable
fun avatar(modifier: Modifier) {
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
            Image(
                painter = painterResource(ic_preview_profile_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,            // crop the image if it's not a square
                modifier = Modifier
                    .size(37.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(2.dp, Color.White, CircleShape)   // add a border (optional)
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun arrudeiaTvItem(modifier: Modifier, item: ArrudeiaTvUIModel) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(percent = 50),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(64.dp)
                .background(color = colorResource(id = colorPrimary))
                .fillMaxSize()
        ) {
            GlideImage(
                loading = placeholder(ColorDrawable(colorResource(id = background_grey_F7F7F9).toArgb())),
                model = item.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun travelItem(onRouteClick: (String) -> Unit, item: TravelUIModel, modifier: Modifier) {

    Column() {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp)
                ) {
                    GlideImage(
                        loading = placeholder(ColorDrawable(colorResource(id = background_grey_F7F7F9).toArgb())),
                        model = item.cover_image_url,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.Crop,
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(6.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(ic_pin),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 2.dp)
                        )
                        Text(
                            text = item.shortLocation(),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(ic_calendar),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 2.dp)
                        )
                        Text(
                            text = item.date(),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                SuggestionChip(
                    modifier = Modifier
                        .height(18.dp)
                        .align(Alignment.Top),
                    colors = suggestionChipColors(containerColor = colorResource(id = colorPrimary)),
                    border = suggestionChipBorder(borderColor = Color.Transparent),
                    onClick = { },
                    label = {
                        Text(item.price.toCurrencyReal(), fontSize = 11.sp, color = Color.White)
                    }
                )
            }
        }
    }
}


@Composable
fun notification(modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(37.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .align(CenterHorizontally)
                .background(color = Color.White)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(ic_notification_on),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.White)
                    .align(CenterHorizontally)
            )
        }
    }
}