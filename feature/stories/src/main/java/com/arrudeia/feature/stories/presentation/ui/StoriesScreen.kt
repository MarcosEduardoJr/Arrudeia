package com.arrudeia.feature.stories.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.stories.presentation.model.StoriesUIModel
import com.arrudeia.feature.stories.presentation.viewmodel.StoriesViewModel
import com.arrudeia.feature.stories.presentation.viewmodel.StoriesViewModel.StoriesUiState


@Composable
fun storiesRoute(
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
                text = (uiState as StoriesUiState.Error).message?.let { stringResource(it) }.orEmpty(),
                modifier = Modifier.padding(4.dp)
            )
        }

        is StoriesUiState.Success -> {
            storiesScreen(
                (uiState as StoriesUiState.Success).list,
                onBackClick
            )

        }
    }
}

@SuppressLint("DesignSystem")
@Composable
internal fun storiesScreen(images: List<StoriesUIModel>, onBackClick: () -> Unit) {

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
                    var currentPage by rememberSaveable { mutableIntStateOf(0) }
                    image(images, currentPage)
                    close(
                        onBackClick, Modifier.Companion
                            .align(Alignment.TopEnd)
                            .padding(top = 32.dp, end = 16.dp)
                    )
                    navigation(
                        currentPage,   {currentPage = it }, images, Modifier.Companion
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}



