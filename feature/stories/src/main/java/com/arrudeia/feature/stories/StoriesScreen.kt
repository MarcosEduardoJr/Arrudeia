package com.arrudeia.feature.stories

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                (uiState as StoriesUiState.Success).list
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
internal fun StoriesScreen(images: List<StoriesUIModel>) {

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

                    Stories(
                        numberOfPages = images.size,
                        onComplete = {}) {
                        GlideImage(
                            model = images[it].image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}


