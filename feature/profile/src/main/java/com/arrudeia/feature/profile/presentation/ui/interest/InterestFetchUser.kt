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
fun InterestFetchUser(
    viewModel: ProfileAboutMeViewModel,
    interestsChange: (String) -> Unit,
    biographyChange: (String) -> Unit,
    onShowFormChange: (Boolean) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    previousInterestChange: (String) -> Unit,
) {
    val uiState by viewModel.sharedFlowInterestsUpdateUser.collectAsStateWithLifecycle()
    when (uiState) {
        is ProfileAboutMeViewModel.InterestsUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is ProfileAboutMeViewModel.InterestsUiState.Error -> {
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
        }


        is ProfileAboutMeViewModel.InterestsUiState.Success -> {
            val user = (uiState as ProfileAboutMeViewModel.InterestsUiState.Success).data
            biographyChange(user.biography.orEmpty())
            previousInterestChange(user.interests.orEmpty())
            onShowFormChange(true)
        }

    }
}