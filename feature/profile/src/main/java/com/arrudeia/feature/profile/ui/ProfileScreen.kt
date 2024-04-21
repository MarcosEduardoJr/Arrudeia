package com.arrudeia.feature.profile.ui

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
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
import com.arrudeia.core.designsystem.R.color.colorBlack
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.R.drawable.ic_arrow_right
import com.arrudeia.core.designsystem.R.drawable.ic_home
import com.arrudeia.core.designsystem.R.drawable.ic_logout
import com.arrudeia.core.designsystem.R.drawable.ic_profile_edit
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.model.ProfileUiModel
import com.arrudeia.feature.profile.viewmodel.ProfileViewModel
import com.arrudeia.feature.profile.viewmodel.ProfileViewModel.ProfileUiState
import com.arrudeia.navigation.profileAddressRoute
import com.arrudeia.navigation.profilePersonalInformationRoute
import com.arrudeia.navigation.signRoute
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileRoute(
    onBackClick: () -> Unit,
    onRouteClick: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    viewModel.getUserPersonalInformation()
    ProfileView(onRouteClick = onRouteClick, viewModel, onShowSnackbar, onBackClick)
}


@Composable
private fun ProfileView(
    onRouteClick: (String) -> Unit,
    viewModel: ProfileViewModel,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.sharedFlow.collectAsStateWithLifecycle()
    when (uiState) {
        is ProfileUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is ProfileUiState.Error -> {
            val message =
                stringResource((uiState as ProfileUiState.Error).message)
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                }
            }
        }

        is ProfileUiState.Success -> {
            val user =
                (uiState as ProfileUiState.Success).data
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

                        header(modifier = Modifier.fillMaxWidth())

                        Spacer(modifier = Modifier.size(30.dp))


                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .verticalScroll(rememberScrollState())
                        ) {

                            profileShorDetail(
                                Modifier
                                    .align(Alignment.Center), user
                            )
                        }

                        Spacer(modifier = Modifier.size(40.dp))

                        items(
                            ic_profile_edit,
                            stringResource(id = R.string.personal_information),
                            Modifier.clickable { onRouteClick(profilePersonalInformationRoute) })

                        Spacer(modifier = Modifier.size(4.dp))

                        items(
                            ic_home,
                            stringResource(id = R.string.address),
                            Modifier.clickable {
                                onRouteClick(profileAddressRoute)
                            })

                        Spacer(modifier = Modifier.size(4.dp))

                        items(
                            ic_logout,
                            stringResource(id = R.string.logout),
                            Modifier.clickable {
                                logout(
                                    onRouteClick, viewModel
                                )
                            })
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
}

fun logout(onRouteClick: (String) -> Unit, viewModel: ProfileViewModel) {
    viewModel.logout()
    onRouteClick(signRoute)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun items(icon: Int, name: String, modifier: Modifier) {

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

                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.White),
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(6.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
                Image(
                    painter = painterResource(ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 2.dp),
                    colorFilter = ColorFilter.tint(colorResource(id = colorBlack))
                )
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun profileShorDetail(modifier: Modifier, item: ProfileUiModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
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
                    .size(184.dp)
                    .background(color = colorResource(id = colorPrimary))
                    .fillMaxSize()
            ) {
                GlideImage(
                    loading = placeholder(ColorDrawable(colorResource(id = background_grey_F7F7F9).toArgb())),
                    model = item.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                )
            }

        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = item.name.orEmpty(),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = item.email.orEmpty(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun header(
    modifier: Modifier,
) {
    Box(
        modifier = modifier
    ) {
        title(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun title(modifier: Modifier) {
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(R.string.profile),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    }
}








