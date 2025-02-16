package com.arrudeia.feature.profile.presentation.ui.profile

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.common.R.string.about_me
import com.arrudeia.core.common.R.string.address
import com.arrudeia.core.common.R.string.logout
import com.arrudeia.core.data.navigation.profileAddressRoute
import com.arrudeia.core.data.navigation.profileInterestRoute
import com.arrudeia.core.data.navigation.profilePersonalInformationRoute
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.component.RectangleCircleBorderItemButton
import com.arrudeia.feature.profile.presentation.model.ProfileUiModel
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileViewModel

@Composable
fun profileContent(
    user: ProfileUiModel,
    onRouteClick: (String) -> Unit,
    viewModel: ProfileViewModel,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background_grey_F7F7F9))
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
                profileHeader(
                    user, Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .verticalScroll(rememberScrollState())
                )

                Spacer(modifier = Modifier.size(2.dp))

                profileOptions(onRouteClick, viewModel)
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
                backgroundColor = colorResource(id = R.color.background_grey_F7F7F9),
                iconSize = 50.dp,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun profileOptions(
    onRouteClick: (String) -> Unit,
    viewModel: ProfileViewModel
) {
    RectangleCircleBorderItemButton(
        iconStart = R.drawable.ic_profile_edit,
        name = stringResource(id = com.arrudeia.feature.profile.R.string.personal_information),
        modifier = Modifier.clickable { onRouteClick(profilePersonalInformationRoute) })

    Spacer(modifier = Modifier.size(10.dp))

    RectangleCircleBorderItemButton(
        iconStart = R.drawable.ic_home,
        name = stringResource(id = address),
        modifier = Modifier.clickable {
            onRouteClick(profileAddressRoute)
        })

    Spacer(modifier = Modifier.size(10.dp))

    RectangleCircleBorderItemButton(
        iconStart = R.drawable.ic_hiking,
        name = stringResource(id = about_me),
        modifier = Modifier.clickable {
            onRouteClick(profileInterestRoute)
        })

    Spacer(modifier = Modifier.size(10.dp))

    RectangleCircleBorderItemButton(
        iconStart =
        R.drawable.ic_logout,
        name = stringResource(id = logout),
        modifier = Modifier.clickable {
            logout(
                onRouteClick, viewModel
            )
        })
}

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
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 2.dp),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.colorBlack))
                )
            }
        }
    }
}


