package com.arrudeia.feature.profile.presentation.ui.personal

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
import com.arrudeia.feature.profile.presentation.viewmodel.ProfilePersonalInformationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun updateUser(
    viewModel: ProfilePersonalInformationViewModel,
    name: String,
    email: String,
    phone: String,
    docId: String,
    birthDate: String,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    updateUser: (Boolean) -> Unit,
) {

    viewModel.savePersonalInformation(
        name, docId, email, phone, birthDate
    )

    val uiState by viewModel.sharedFlowUpdateUser.collectAsStateWithLifecycle()
    when (uiState) {
        is ProfilePersonalInformationViewModel.PersonalInformationUpdateUserUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is ProfilePersonalInformationViewModel.PersonalInformationUpdateUserUiState.Error -> {
            val message =
                stringResource(
                    (uiState as ProfilePersonalInformationViewModel.PersonalInformationUpdateUserUiState.Error).message
                )
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                }
            }
            updateUser(false)
        }

        is ProfilePersonalInformationViewModel.PersonalInformationUpdateUserUiState.Success -> {
            val message =
                stringResource(
                    (uiState
                            as ProfilePersonalInformationViewModel.PersonalInformationUpdateUserUiState.Success
                            ).message
                )
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                    updateUser(false)
                }
            }
        }

    }
}