package com.arrudeia.feature.trip

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.component.NiaButtonColor
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.trip.R.drawable.ic_bg_onboarding
import com.arrudeia.feature.trip.R.string.tools_title_trip_detail_name
import com.arrudeia.feature.trip.R.string.tools_currency_price
import com.arrudeia.feature.trip.R.string.from
import com.arrudeia.feature.trip.R.string.tools_subtitle_trip_detail_name
import com.arrudeia.feature.trip.R.string.description
import com.arrudeia.feature.trip.R.string.tools_lorem_ipsum

import com.arrudeia.feature.trip.R.string.talk_with_agency
import com.arrudeia.navigation.homeRoute


private const val LINK_1 = "link_1"
private const val LINK_2 = "link_2"
private const val SPACING_FIX = 3f


@Composable
internal fun TripDetailRoute(
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: TripDetailViewModel = hiltViewModel(),
) {
    TripDetail(
        onRouteClick
    )
}

@SuppressLint("DesignSystem")
@Composable
internal fun TripDetail(onRouteClick: (String) -> Unit) {
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
                    Image(
                        painter = painterResource(ic_bg_onboarding),
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
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxSize()
                                        .clipToBounds(),
                                ) {
                                    Text(
                                        text = stringResource(tools_title_trip_detail_name),
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
                                            text = stringResource(tools_subtitle_trip_detail_name),
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
                                                text = stringResource(tools_currency_price),
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
                                        text = stringResource(tools_lorem_ipsum),
                                        color = Color.Gray,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Start
                                    )
                                }
                                NiaButtonColor(
                                    onClick = { onRouteClick(homeRoute) },
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(16.dp).fillMaxWidth(),
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
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewAux() {
    TripDetail {}
}

