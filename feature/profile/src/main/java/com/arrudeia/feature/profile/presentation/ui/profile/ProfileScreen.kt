package com.arrudeia.feature.profile.presentation.ui.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.data.navigation.signRoute
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileViewModel
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileViewModel.ProfileUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun profileRoute(
    onBackClick: () -> Unit,
    onRouteClick: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    viewModel.getUserPersonalInformation()
    profileView(onRouteClick = onRouteClick, viewModel, onShowSnackbar, onBackClick)
}


@Composable
private fun profileView(
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
                (uiState as ProfileUiState.Error).message?.let { stringResource(it) }.orEmpty()
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                }
            }
        }

        is ProfileUiState.Success -> {
            val user =
                (uiState as ProfileUiState.Success).data
            profileContent(user, onRouteClick, viewModel, onBackClick)
        }
    }
}

fun logout(onRouteClick: (String) -> Unit, viewModel: ProfileViewModel) {
    viewModel.logout()
    onRouteClick(signRoute)
}