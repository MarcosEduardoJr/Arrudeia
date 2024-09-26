package com.arrudeia.feature.social.presentation.ui

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.data.navigation.profileRoute
import com.arrudeia.core.designsystem.component.HtmlText
import com.arrudeia.feature.social.R
import com.arrudeia.feature.social.presentation.viewmodel.SocialViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.bumptech.glide.integration.compose.placeholder


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SocialHeader(
    modifier: Modifier,
    onRouteClick: (String) -> Unit,
    viewModel: SocialViewModel = hiltViewModel()
) {
    viewModel.getUserPersonalInformation()
    val image = viewModel.imgUserUrl

    Row(
        modifier = modifier
    ) {

        HtmlText(
            modifier = Modifier
                .weight(1f),
            html = stringResource(id = R.string.messageHeaderSocial),
            normalColor = com.arrudeia.core.designsystem.R.color.colorBlack,
            alignmentText = View.TEXT_ALIGNMENT_TEXT_START,
            textStyle = MaterialTheme.typography.titleMedium
        )
        SocialImageHeader(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterVertically)
                .clickable { onRouteClick(profileRoute) },
            image.value,
            placeholder(com.arrudeia.core.designsystem.R.drawable.ic_arrudeia_logo),
            null
        )
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SocialImageHeader(
    modifier: Modifier,
    image: String,
    placeholder: Placeholder,
    colorIcon: ColorFilter?
) {

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
            GlideImage(
                loading = placeholder,
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape),
                failure = placeholder,
                colorFilter = colorIcon
            )
        }
    }
}