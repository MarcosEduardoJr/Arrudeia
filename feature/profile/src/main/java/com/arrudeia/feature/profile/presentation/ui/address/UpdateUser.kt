package com.arrudeia.feature.profile.presentation.ui.address

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
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAddressViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun updateAddressUser(
    viewModel: ProfileAddressViewModel,
    zipCode: String,
    street: String,
    number: String,
    district: String,
    city: String,
    state: String,
    country: String,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    updateUser: (Boolean) -> Unit,
) {

    viewModel.saveAddress(
        zipCode, street, number.toInt(), district, city, state, country
    )

    val uiState by viewModel.sharedFlowUpdateUser.collectAsStateWithLifecycle()
    when (uiState) {
        is ProfileAddressViewModel.PersonalInformationUpdateUserUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is ProfileAddressViewModel.PersonalInformationUpdateUserUiState.Error -> {
            val message =
                stringResource((uiState as ProfileAddressViewModel.PersonalInformationUpdateUserUiState.Error).message)
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                }
            }
            updateUser(false)
        }

        is ProfileAddressViewModel.PersonalInformationUpdateUserUiState.Success -> {
            val message =
                stringResource(
                    (uiState as ProfileAddressViewModel.PersonalInformationUpdateUserUiState.Success).message
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
