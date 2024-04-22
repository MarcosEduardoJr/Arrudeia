package com.arrudeia.feature.trip

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.Html
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.trip.R.drawable.ic_bg_onboarding
import com.arrudeia.feature.trip.R.string.description
import com.arrudeia.feature.trip.R.string.from
import com.arrudeia.feature.trip.R.string.talk_with_agency
import com.arrudeia.feature.trip.R.string.whatsapp_contact_message
import com.arrudeia.feature.trip.R.string.tools_currency_price
import com.arrudeia.feature.trip.R.string.tools_lorem_ipsum
import com.arrudeia.feature.trip.R.string.tools_subtitle_trip_detail_name
import com.arrudeia.feature.trip.R.string.tools_title_trip_detail_name
import com.arrudeia.feature.trip.TripDetailViewModel.TripDetailUiState.Success
import com.arrudeia.feature.trip.TripDetailViewModel.TripDetailUiState.Loading
import com.arrudeia.feature.trip.TripDetailViewModel.TripDetailUiState.Error
import com.arrudeia.feature.trip.model.TripUIModel
import com.arrudeia.util.toCurrencyReal
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

private const val LINK_1 = "link_1"
private const val LINK_2 = "link_2"
private const val SPACING_FIX = 3f


@Composable
internal fun TripDetailRoute(
    viewModel: TripDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val arrTvUiState by viewModel.travelSharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchData()

    when (arrTvUiState) {
        is Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is Error -> {
            Text(
                text = stringResource((arrTvUiState as Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is Success -> {
            val item = (arrTvUiState as Success).item
            TripDetail(
                item,
                onBackClick
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("DesignSystem")
@Composable
internal fun TripDetail(item: TripUIModel?, onBackClick: () -> Unit) {
    val context = LocalContext.current as Context
    val packageManager = LocalContext.current.packageManager
    val url =
        "https://api.whatsapp.com/send?phone=${item?.whatsapp.orEmpty()}&text=${
            stringResource(
                id = whatsapp_contact_message,
                item?.shortLocation().toString(),
                item?.date().orEmpty()
            )
        }"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    ArrudeiaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorResource(id = background_grey_F7F7F9)
            ) {
                Box(
                    modifier = with(Modifier) {
                        fillMaxSize()
                    })
                {
                    GlideImage(
                        model = item?.cover_image_url,
                        contentDescription = null,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                    )
                    Column(
                        modifier = Modifier
                            .padding(top = 260.dp)
                            .fillMaxSize()
                            .clipToBounds(),
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .clipToBounds(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = colorResource(id = background_grey_F7F7F9),
                                contentColor = colorResource(id = background_grey_F7F7F9),
                                disabledContainerColor = colorResource(id = background_grey_F7F7F9),
                                disabledContentColor = colorResource(id = background_grey_F7F7F9)
                            )
                        ) {
                            Box(
                                modifier = with(Modifier) {
                                    fillMaxSize()
                                })
                            {
                                LazyColumn(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxSize()
                                        .clipToBounds(),
                                ) {
                                    items(1) {

                                        Text(
                                            text = item?.name.orEmpty(),
                                            color = Color.Black,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Start
                                        )

                                        Box(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                modifier = Modifier
                                                    .align(Alignment.CenterStart),
                                                text = item?.shortLocation().orEmpty(),
                                                color = Color.Gray,
                                                fontSize = 14.sp,
                                                textAlign = TextAlign.Start
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .align(Alignment.CenterEnd)
                                            ) {
                                                Text(
                                                    text = stringResource(from),
                                                    color = Color.Gray,
                                                    fontSize = 14.sp,
                                                    textAlign = TextAlign.Start
                                                )
                                                Spacer(modifier = Modifier.size(2.dp))
                                                Text(
                                                    text = item?.price?.toCurrencyReal().orEmpty(),
                                                    color = colorResource(id = colorPrimary),
                                                    fontSize = 14.sp,
                                                    textAlign = TextAlign.Start
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(20.dp))
                                        Text(
                                            text = stringResource(description),
                                            color = Color.Black,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Start
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = item?.description.orEmpty(),
                                            color = Color.Gray,
                                            fontSize = 14.sp,
                                            textAlign = TextAlign.Start,
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        include(item)
                                        optional(item)
                                        Spacer(modifier = Modifier.size(50.dp))
                                    }
                                }
                                ArrudeiaButtonColor(
                                    onClick = {
                                        context.startActivity(intent)
                                    },
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    colorButton = colorResource(colorPrimary),
                                ) {
                                    Text(
                                        text = stringResource(id = talk_with_agency),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                    )
                                }
                            }
                        }
                    }
                }
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
                    backgroundColor = colorResource(id = background_grey_F7F7F9),
                    iconSize = 50.dp
                )


            }
        }
    }
}

@Composable
private fun optional(item: TripUIModel?) {
    if (item?.optional.orEmpty().isNotEmpty()) {
        Text(
            text = stringResource(R.string.optional),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clipToBounds(),
        ) {
            item?.optional.orEmpty().forEach {
                Row {
                    Image(
                        Icons.Rounded.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        colorFilter = ColorFilter.tint(color = colorResource(id = colorPrimary))
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = it,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

@Composable
private fun include(item: TripUIModel?) {
    if (item?.include.orEmpty().isNotEmpty()) {
        Text(
            text = stringResource(R.string.include),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .fillMaxWidth(),
        ) {
            item?.include?.forEach {
                Row {
                    Image(
                        Icons.Rounded.Check,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        colorFilter = ColorFilter.tint(color = colorResource(id = colorPrimary))
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = it,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

@Composable
fun OpenWhatsapp(number: String) {

}


