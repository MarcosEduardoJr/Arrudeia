package com.arrudeia.feature.social.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SocialEvents() {
    val textGrey = colorResource(id = R.color.text_grey)
    val placeholder = placeholder(R.drawable.ic_arrudeia_logo)
    val img =
        "https://firebasestorage.googleapis.com/v0/b/arrudeia-5fedc.appspot.com/o/travels%2Fparque-nacional-do-catimbau.jpg?alt=media&token=d35fed86-cbfb-437e-84f7-cdbdfc29ed59"
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            items(1) {

                Card(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        Card(
                            modifier = Modifier
                                .align(Alignment.TopCenter),
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            Box(
                                modifier = Modifier
                            ) {
                                GlideImage(
                                    model = img,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    loading = placeholder,
                                    failure = placeholder,
                                    colorFilter = null
                                )
                            }
                        }
                        Card(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 90.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 8.dp,
                                        top = 0.dp
                                    )
                                ) {


                                    Row {

                                        Text(
                                            text = "Vale do Catimbau",
                                            modifier = Modifier
                                                .padding(top = 4.dp)
                                                .weight(1f),
                                            Color.Black,
                                            textAlign = TextAlign.Start,
                                            style = MaterialTheme.typography.titleMedium,
                                        )

                                        Icon(
                                            painterResource(id = R.drawable.person),
                                            tint = colorResource(id = R.color.text_grey),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(14.dp)
                                                .align(Alignment.CenterVertically)
                                        )
                                        Text(
                                            text = "1",
                                            modifier = Modifier.align(Alignment.CenterVertically),
                                            color = Color.Black,
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }

                                    Row(modifier = Modifier.padding(top = 4.dp)) {

                                        Icon(
                                            painterResource(id = R.drawable.calendar),
                                            tint = colorResource(id = R.color.text_grey),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .padding(end = 8.dp)
                                        )

                                        Text(
                                            text = "10/10/2021 - 15/10/2021 ",
                                            modifier = Modifier.align(Alignment.CenterVertically),
                                            Color.Black,
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.bodySmall,
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        val titleAddNewPlace = stringResource(id = com.arrudeia.feature.social.R.string.create_a_plan)
        ExtendedFloatingActionButton(
            text = { Text(text = titleAddNewPlace) },
            icon = { Icon(Icons.Rounded.Add, null) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            shape = CircleShape,
            onClick = {
                //  callingNewService = true
                //  viewModel.checkHasIdentificationDoc()
            },
            containerColor = colorResource(id = R.color.colorPrimary),
            contentColor = Color.White
        )
    }
}
