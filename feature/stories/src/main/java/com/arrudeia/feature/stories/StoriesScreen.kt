package com.arrudeia.feature.stories

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.icon.ArrudeiaIcons
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.stories.R.drawable.ic_bg_onboarding
import com.arrudeia.feature.stories.stories.Stories

import com.arrudeia.core.designsystem.R.color.colorWhite
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.feature.stories.StoriesViewModel.StoriesUiState
import com.arrudeia.feature.stories.model.StoriesUIModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

private const val LINK_1 = "link_1"
private const val LINK_2 = "link_2"
private const val SPACING_FIX = 3f


@Composable
fun StoriesRoute(
    onStoriesId: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: StoriesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.storiesSharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchStories()
    when (uiState) {
        is StoriesUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is StoriesUiState.Error -> {
            Text(
                text = stringResource((uiState as StoriesUiState.Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is StoriesUiState.Success -> {
            StoriesScreen(
                (uiState as StoriesUiState.Success).list,
                onBackClick
            )

        }

        else -> {
            Toast.makeText(
                LocalContext.current,
                uiState.toString(),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("DesignSystem")
@Composable
internal fun StoriesScreen(images: List<StoriesUIModel>, onBackClick: () -> Unit) {

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

                Box(contentAlignment = Alignment.TopEnd) {


                    var currentPage by rememberSaveable { mutableStateOf(0) }


                    Stories(
                        numberOfPages = images.size,
                        onComplete = {},
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
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 32.dp, end = 16.dp)
                    ) {
                        Icon(
                            Icons.Rounded.Close, null, modifier = Modifier
                                .size(36.dp)
                                .clickable {
                                    onBackClick()
                                },
                            tint = colorResource(id = com.arrudeia.core.designsystem.R.color.colorPrimary)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        Row {
                            CircularIconButton(
                                onClick = {
                                    if (currentPage > 0)
                                        currentPage--
                                },
                                icon = Icons.Rounded.ArrowBack,
                                backgroundColor = colorResource(id = background_grey_F7F7F9),
                                iconSize = 60.dp
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            CircularIconButton(
                                onClick = {
                                    if (currentPage < images.size)
                                        currentPage++
                                },
                                icon = Icons.Rounded.ArrowForward,
                                backgroundColor = colorResource(id = background_grey_F7F7F9),
                                iconSize = 60.dp
                            )
                        }

                    }
                }
            }
        }
    }
}


