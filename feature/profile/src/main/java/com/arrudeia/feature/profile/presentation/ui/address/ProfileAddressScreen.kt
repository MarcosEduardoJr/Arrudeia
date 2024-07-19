package com.arrudeia.feature.profile.presentation.ui.address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.R.drawable.ic_home
import com.arrudeia.core.designsystem.R.drawable.ic_number_123
import com.arrudeia.core.designsystem.R.drawable.ic_pin_drop
import com.arrudeia.core.designsystem.R.drawable.ic_street
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.component.TextFieldInput
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAddressViewModel
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAddressViewModel.AddressUiState
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAddressViewModel.PersonalInformationUpdateUserUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun profileAddressRoute(
    onBackClick: () -> Unit,
    viewModel: ProfileAddressViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    viewModel.getUserPersonalInformation()

    var zipCode by rememberSaveable { mutableStateOf("") }
    var street by rememberSaveable { mutableStateOf("") }
    var number by rememberSaveable { mutableStateOf("") }
    var district by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable { mutableStateOf("") }
    var country by rememberSaveable { mutableStateOf("") }
    var showForm by rememberSaveable { mutableStateOf(false) }

    screenView(
        viewModel,
        zipCode,
        { zipCode = it },
        street,
        { street = it },
        number,
        { number = it },
        district,
        { district = it },
        city,
        { city = it },
        state,
        { state = it },
        country,
        { country = it },
        showForm,
        { showForm = it },
        onShowSnackbar,
        onBackClick

    )

}
@Suppress("LongMethod")
@Composable
fun screenView(
    viewModel: ProfileAddressViewModel,
    zipCode: String,
    zipCodeChange: (String) -> Unit,
    street: String,
    streetChange: (String) -> Unit,
    number: String,
    numberChange: (String) -> Unit,
    district: String,
    districtChange: (String) -> Unit,
    city: String,
    cityChange: (String) -> Unit,
    state: String,
    stateChange: (String) -> Unit,
    country: String,
    countryChange: (String) -> Unit,
    showForm: Boolean,
    onShowFormChange: (Boolean) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit,
) {
    var updatingUser by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = background_grey_F7F7F9))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            CircularIconButton(
                onClick = {
                    onBackClick()
                },
                icon = Icons.Rounded.ArrowBack,
                backgroundColor = colorResource(id = background_grey_F7F7F9),
                iconSize = 50.dp,
                modifier = Modifier
            )
        }

        formBehaviour(
            showForm,
            zipCode,
            zipCodeChange,
            street,
            streetChange,
            number,
            numberChange,
            district,
            districtChange,
            city,
            cityChange,
            state,
            stateChange,
            country,
            countryChange,
            viewModel,
            onShowFormChange,
            onShowSnackbar,
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
        var colorButton: Color
        var clickButton: () -> Unit

        if (updatingUser) {
            updateAddressUser(
                viewModel = viewModel,
                zipCode,
                street,
                number,
                district,
                city,
                state,
                country,
                onShowSnackbar = onShowSnackbar,
                updateUser = { updatingUser = it }
            )
            colorButton = colorResource(background_grey_F7F7F9)
            clickButton = {}
        } else {
            colorButton = colorResource(colorPrimary)
            clickButton = {
                updatingUser = true
            }
        }
        updateButton(
            clickButton, colorButton, Modifier.Companion
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}
