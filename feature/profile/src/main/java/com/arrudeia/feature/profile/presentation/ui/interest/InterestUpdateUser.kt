package com.arrudeia.feature.profile.presentation.ui.interest

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAboutMeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun InterestUpdateUser(
    viewModel: ProfileAboutMeViewModel,
    interests: String,
    biography: String,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    updateUser: (Boolean) -> Unit,
) {
    viewModel.saveInterests(interests, biography)

    val uiState by viewModel.updateSharedFlowInterestsUpdateUser.collectAsStateWithLifecycle()
    when (uiState) {
        is ProfileAboutMeViewModel.UpdateInterestsUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )

        }

        is ProfileAboutMeViewModel.UpdateInterestsUiState.Error -> {
            val message =
                (uiState as ProfileAboutMeViewModel.InterestsUiState.Error).message?.let {
                    stringResource(
                        it
                    )
                }
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (message != null) {
                        onShowSnackbar(message, "")
                    }
                }
            }
            updateUser(false)
        }

        is ProfileAboutMeViewModel.UpdateInterestsUiState.Success -> {
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    updateUser(false)
                }
            }
        }
    }
}
