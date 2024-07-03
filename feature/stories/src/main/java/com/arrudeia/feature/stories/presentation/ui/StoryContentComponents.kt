package com.arrudeia.feature.stories.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.feature.stories.presentation.model.StoriesUIModel
import com.arrudeia.feature.stories.presentation.stories.stories
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun image(
    images: List<StoriesUIModel>,
    currentPage: Int
) {
    stories(
        numberOfPages = images.size,
        currentPage = currentPage
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            GlideImage(
                model = images[it].image,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun close(onBackClick: () -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        Icon(
            Icons.Rounded.Close, null, modifier = Modifier
                .size(36.dp)
                .clickable {
                    onBackClick()
                },
            tint = colorResource(id = R.color.colorPrimary)
        )
    }
}

@Composable
fun navigation(
    currentPage: Int,
    currentPageChanged: (Int) -> Unit,
    images: List<StoriesUIModel>,
    modifier: Modifier
) {
    var currentPageAux =currentPage
    Box(
        modifier = modifier
    ) {
        Row {
            CircularIconButton(
                onClick = {
                    if (currentPageAux > 0)
                        currentPageChanged(--currentPageAux)
                },
                icon = Icons.Rounded.ArrowBack,
                backgroundColor = colorResource(id = R.color.background_grey_F7F7F9),
                iconSize = 60.dp,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.size(10.dp))
            CircularIconButton(
                onClick = {
                    if (currentPageAux < images.size)
                        currentPageChanged(++currentPageAux)
                },
                icon = Icons.Rounded.ArrowForward,
                backgroundColor = colorResource(id = R.color.background_grey_F7F7F9),
                iconSize = 60.dp,
                modifier = Modifier
            )
        }

    }
}