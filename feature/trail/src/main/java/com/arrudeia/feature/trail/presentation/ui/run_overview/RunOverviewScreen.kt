@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.arrudeia.feature.trail.presentation.ui.run_overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.data.navigation.activeRunRoute
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.theme.HikingIcon
import com.arrudeia.feature.trail.R.string.empty_list_trail
import com.arrudeia.feature.trail.R.string.trail
import com.arrudeia.feature.trail.presentation.ui.run_overview.components.RunListItem
import com.arrudeia.feature.trail.presentation.viewmodel.RunOverviewViewModel

@Composable
fun RunOverviewScreenRoot(
    onActiveRunClick: (String) -> Unit,
    viewModel: RunOverviewViewModel = hiltViewModel(),
) {
    RunOverviewScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                RunOverviewAction.OnStartClick -> onActiveRunClick(activeRunRoute)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RunOverviewScreen(
    state: RunOverviewState,
    onAction: (RunOverviewAction) -> Unit
) {


    ArrudeiaScaffold(
        topAppBar = {},
        floatingActionButton = {
            ArrudeiaFloatingActionButton(
                icon = HikingIcon,
                onClick = {
                    onAction(RunOverviewAction.OnStartClick)
                }
            )
        }
    ) { padding ->
        if (state.runs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = empty_list_trail), color = Color.Black)
            }
        } else
            Column {

                Text(
                    text = stringResource(id = trail),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(vertical = 32.dp)
                        .align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = padding,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = state.runs,
                        key = { it.id }
                    ) {
                        RunListItem(
                            runUi = it,
                            onDeleteClick = {
                                onAction(RunOverviewAction.DeleteRun(it))
                            },
                            modifier = Modifier
                                .animateItemPlacement()
                        )
                    }
                }
            }
    }
}

@Composable
fun ArrudeiaScaffold(
    modifier: Modifier = Modifier,
    withGradient: Boolean = true,
    topAppBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topAppBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.End,
        modifier = modifier
    ) { padding ->
        content(padding)
    }
}


@Composable
fun ArrudeiaFloatingActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconSize: Dp = 25.dp
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(colorResource(id = R.color.colorPrimary))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(iconSize)
        )
    }
}