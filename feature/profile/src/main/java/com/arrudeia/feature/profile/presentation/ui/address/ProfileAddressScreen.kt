package com.arrudeia.feature.profile.presentation.ui.address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAddressViewModel

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
                .align(Alignment.TopCenter),
            onBackClick
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
                .align(Alignment.TopEnd)
                .padding(top = 30.dp,end =16.dp)
        )
    }
}
