package com.arrudeia.feature.profile.presentation.ui

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


@Composable
fun form(
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
) {
    Column(Modifier.padding(horizontal = 16.dp)) {

        TextFieldInput(
            hint = stringResource(id = R.string.zip_code),
            zipCode,
            icon = painterResource(id = ic_home),
            onValueChange = zipCodeChange,
            KeyboardType.Number,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.street),
            street,
            icon = painterResource(id = ic_street),
            onValueChange = streetChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.number),
            value = number,
            icon = painterResource(id = ic_number_123),
            onValueChange = numberChange,
            KeyboardType.Number,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.district),
            district,
            icon = painterResource(id = ic_pin_drop),
            onValueChange = districtChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.city),
            city,
            icon = painterResource(id = ic_pin_drop),
            onValueChange = cityChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.state),
            state,
            icon = painterResource(id = ic_pin_drop),
            onValueChange = stateChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.country),
            country,
            icon = painterResource(id = ic_pin_drop),
            onValueChange = countryChange,
            KeyboardType.Text,
            ImeAction.Done
        )

    }
}

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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {

            Spacer(modifier = Modifier.size(30.dp))

            header(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.size(30.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .verticalScroll(rememberScrollState())
            ) {


            }

            Spacer(modifier = Modifier.size(40.dp))

            if (showForm)
                form(
                    zipCode, zipCodeChange,
                    street, streetChange,
                    number, numberChange,
                    district, districtChange,
                    city, cityChange,
                    state, stateChange,
                    country, countryChange
                )
            else {
                fetchUser(
                    viewModel,
                    zipCodeChange,
                    streetChange,
                    numberChange,
                    districtChange,
                    cityChange,
                    stateChange,
                    countryChange,
                    onShowFormChange,
                    onShowSnackbar
                )
            }
        }
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
        ArrudeiaButtonColor(
            onClick = clickButton,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            colorButton = colorButton,
        ) {
            Text(
                text = stringResource(id = R.string.save),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )
        }

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
                iconSize = 50.dp
            )


        }
    }
}


@Composable
private fun updateAddressUser(
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
        is PersonalInformationUpdateUserUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is PersonalInformationUpdateUserUiState.Error -> {
            val message =
                stringResource((uiState as PersonalInformationUpdateUserUiState.Error).message)
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                }
            }
            updateUser(false)
        }

        is PersonalInformationUpdateUserUiState.Success -> {
            val message =
                stringResource((uiState as PersonalInformationUpdateUserUiState.Success).message)
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                    updateUser(false)
                }
            }
        }

    }
}

@Composable
private fun fetchUser(
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
        is AddressUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is AddressUiState.Error -> {
            val message =
                stringResource((uiState as PersonalInformationUpdateUserUiState.Error).message)
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                }
            }
        }


        is AddressUiState.Success -> {
            val user = (uiState as AddressUiState.Success).data
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




@Composable
private fun header(
    modifier: Modifier,
) {
    Box(
        modifier = modifier
    ) {
        title(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun title(modifier: Modifier) {
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(R.string.address),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    }
}
