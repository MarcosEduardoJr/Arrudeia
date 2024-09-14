package com.arrudeia.feature.checklist.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.checklist.R
import com.arrudeia.feature.checklist.presentation.model.CheckListUIModel
import com.arrudeia.feature.checklist.presentation.viewmodel.CheckListViewModel
import com.arrudeia.feature.checklist.presentation.viewmodel.CheckListViewModel.CheckListUiState.Error
import com.arrudeia.feature.checklist.presentation.viewmodel.CheckListViewModel.CheckListUiState.Loading
import com.arrudeia.feature.checklist.presentation.viewmodel.CheckListViewModel.CheckListUiState.Success
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
internal fun CheckListRoute(
    viewModel: CheckListViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.sharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchData()
    when (uiState) {
        is Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is Error -> {
            Text(
                text = stringResource((uiState as Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is Success -> {
            val item = (uiState as Success).item
            tripDetail(
                item,
                onBackClick
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("DesignSystem")
@Composable
internal fun tripDetail(item: List<CheckListUIModel?>, onBackClick: () -> Unit) {
    val context = LocalContext.current

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

                    GlideImage(
                        model = stringResource(R.string.background_checklist_img),
                        contentDescription = null,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillBounds,
                    )

                    CircularIconButton(
                        onClick = {
                            onBackClick()
                        },
                        icon = Icons.Rounded.ArrowBack,
                        backgroundColor = colorResource(id = background_grey_F7F7F9),
                        iconSize = 50.dp,
                        modifier = Modifier.align(Alignment.TopStart).padding(16.dp)
                    )
                    content(item = item)
                }
            }
        }
    }
}
