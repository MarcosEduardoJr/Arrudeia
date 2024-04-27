package com.arrudeia.feature.profile.presentation.ui.address

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.component.TextFieldInput
import com.arrudeia.feature.profile.R


@Suppress("LongMethod")
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
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_home),
            onValueChange = zipCodeChange,
            KeyboardType.Number,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.street),
            street,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_street),
            onValueChange = streetChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.number),
            value = number,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_number_123),
            onValueChange = numberChange,
            KeyboardType.Number,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.district),
            district,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_pin_drop),
            onValueChange = districtChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.city),
            city,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_pin_drop),
            onValueChange = cityChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.state),
            state,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_pin_drop),
            onValueChange = stateChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.country),
            country,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_pin_drop),
            onValueChange = countryChange,
            KeyboardType.Text,
            ImeAction.Done
        )
    }
}