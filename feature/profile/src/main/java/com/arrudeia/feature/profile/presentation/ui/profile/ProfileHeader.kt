package com.arrudeia.feature.profile.presentation.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.common.R.string.profile
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.feature.profile.presentation.model.ProfileUiModel

@Composable
fun profileHeader(user: ProfileUiModel, modifier: Modifier) {
    ProfileHeader(profile)
    Spacer(modifier = Modifier.size(16.dp))

    Box(
        modifier = modifier
    ) {

        profileShorDetail(
            Modifier
                .align(Alignment.Center), user
        )
    }
}

@Composable
fun ProfileHeaderBackButton(title: Int, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        CircularIconButton(
            onClick = {
                onBackClick()
            },
            icon = Icons.Rounded.ArrowBack,
            backgroundColor = colorResource(id = R.color.background_grey_F7F7F9),
            iconSize = 50.dp,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            text = stringResource(title),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProfileHeader(title: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            text = stringResource(title),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

    }
}