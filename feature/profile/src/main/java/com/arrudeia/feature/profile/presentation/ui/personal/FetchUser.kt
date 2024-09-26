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
fun fetchUser(
    viewModel: ProfilePersonalInformationViewModel,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onDocIdChange: (String) -> Unit,
    onBirthDateChange: (String) -> Unit,
    onShowFormChange: (Boolean) -> Unit,
    onProfileImageChange: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    previousGenderChoosed: (String) -> Unit,
) {
    val uiState by viewModel.sharedFlow.collectAsStateWithLifecycle()
    when (uiState) {
        is ProfilePersonalInformationViewModel.PersonalInformationUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is ProfilePersonalInformationViewModel.PersonalInformationUiState.Error -> {
            val message =
                stringResource(
                    (uiState as ProfilePersonalInformationViewModel.PersonalInformationUiState.Error).message
                )
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                }
            }
        }

        is ProfilePersonalInformationViewModel.PersonalInformationUiState.Success -> {
            val user =
                (uiState as ProfilePersonalInformationViewModel.PersonalInformationUiState.Success).data
            onNameChange(user.name.orEmpty())
            onEmailChange(user.email.orEmpty())
            onPhoneChange(user.phone.orEmpty())
            onDocIdChange(user.idDocument.orEmpty())
            onBirthDateChange(user.birthDate.orEmpty())
            onProfileImageChange(user.profileImage.orEmpty())
            previousGenderChoosed(user.gender.orEmpty())
            onShowFormChange(true)
        }

    }
}