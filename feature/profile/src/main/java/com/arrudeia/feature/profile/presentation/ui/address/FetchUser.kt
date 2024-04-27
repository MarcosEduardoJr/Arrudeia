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
fun fetchUser(
    viewModel: ProfileAddressViewModel,
    zipCodeChange: (String) -> Unit,
    streetChange: (String) -> Unit,
    numberChange: (String) -> Unit,
    districtChange: (String) -> Unit,
    cityChange: (String) -> Unit,
    stateChange: (String) -> Unit,
    countryChange: (String) -> Unit,
    onShowFormChange: (Boolean) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val uiState by viewModel.sharedFlow.collectAsStateWithLifecycle()
    when (uiState) {
        is ProfileAddressViewModel.AddressUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is ProfileAddressViewModel.AddressUiState.Error -> {
            val message =
                stringResource((uiState as ProfileAddressViewModel.PersonalInformationUpdateUserUiState.Error).message)
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                }
            }
        }


        is ProfileAddressViewModel.AddressUiState.Success -> {
            val user = (uiState as ProfileAddressViewModel.AddressUiState.Success).data
            zipCodeChange(user.zipCode.orEmpty())
            streetChange(user.street.orEmpty())
            numberChange(if (user.number != -1 && user.number != null) user.number.toString() else "")
            districtChange(user.district.orEmpty())
            cityChange(user.city.orEmpty())
            stateChange(user.state.orEmpty())
            countryChange(user.country.orEmpty())
            onShowFormChange(true)
        }

    }
}