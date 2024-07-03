package com.arrudeia.core.ui.address

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.TextFieldInput
import com.arrudeia.core.utils.isValidZipCode
import com.arrudeia.core.viewmodel.CommonAddressFormViewModel


@Suppress("LongMethod")
@Composable
fun ArrudeiaddressForm(
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
    viewModel: CommonAddressFormViewModel = hiltViewModel(),
) {
    Column {

        val addressByZipCode = viewModel.addressByZipCode.value

        LaunchedEffect(key1 = addressByZipCode) {
            if (addressByZipCode != null) {
                streetChange(addressByZipCode.street)
                districtChange(addressByZipCode.district)
                cityChange(addressByZipCode.city)
                stateChange(addressByZipCode.state)
                countryChange(addressByZipCode.country)
            }
        }

        TextFieldInput(
            hint = stringResource(id = R.string.zip_code),
            zipCode,
            icon = painterResource(id = R.drawable.ic_home),
            onValueChange = {
                zipCodeChange(it)
                if (it.isValidZipCode()) {
                    viewModel.getAddressByZipCode(it)
                }

            },
            KeyboardType.Number,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.street),
            street,
            icon = painterResource(id = R.drawable.ic_street),
            onValueChange = streetChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.number),
            value = number,
            icon = painterResource(id = R.drawable.ic_number_123),
            onValueChange = numberChange,
            KeyboardType.Number,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.district),
            district,
            icon = painterResource(id = R.drawable.ic_pin_drop),
            onValueChange = districtChange,
            KeyboardType.Text,
            ImeAction.Next,

            )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.city),
            city,
            icon = painterResource(id = R.drawable.ic_pin_drop),
            onValueChange = { },
            KeyboardType.Text,
            ImeAction.Next,
            false
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.state),
            state,
            icon = painterResource(id = R.drawable.ic_pin_drop),
            onValueChange = { },
            KeyboardType.Text,
            ImeAction.Next,
            false
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.country),
            country,
            icon = painterResource(id = R.drawable.ic_pin_drop),
            onValueChange = { },
            KeyboardType.Text,
            ImeAction.Done,
            false
        )
    }
}