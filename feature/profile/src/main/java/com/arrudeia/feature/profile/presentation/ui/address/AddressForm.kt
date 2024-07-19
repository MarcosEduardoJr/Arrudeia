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
import com.arrudeia.core.designsystem.R.string.city
import com.arrudeia.core.designsystem.R.string.country
import com.arrudeia.core.designsystem.R.string.district
import com.arrudeia.core.designsystem.R.string.number
import com.arrudeia.core.designsystem.R.string.state
import com.arrudeia.core.designsystem.R.string.street
import com.arrudeia.core.designsystem.R.string.zip_code
import com.arrudeia.core.designsystem.component.TextFieldInput

@Suppress("LongMethod")
@Composable
fun form(
    zipCodeParam: String,
    zipCodeParamChange: (String) -> Unit,
    streetParam: String,
    streetParamChange: (String) -> Unit,
    numberParam: String,
    numberParamChange: (String) -> Unit,
    districtParam: String,
    districtParamChange: (String) -> Unit,
    cityParam: String,
    cityParamChange: (String) -> Unit,
    stateParam: String,
    stateParamChange: (String) -> Unit,
    countryParam: String,
    countryParamChange: (String) -> Unit,
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        TextFieldInput(
            hint = stringResource(id = zip_code),
            zipCodeParam,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_home),
            onValueChange = zipCodeParamChange,
            KeyboardType.Number,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = street),
            streetParam,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_street),
            onValueChange = streetParamChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = number),
            value = numberParam,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_number_123),
            onValueChange = numberParamChange,
            KeyboardType.Number,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = district),
            districtParam,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_pin_drop),
            onValueChange = districtParamChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = city),
            cityParam,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_pin_drop),
            onValueChange = cityParamChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = state),
            stateParam,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_pin_drop),
            onValueChange = stateParamChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextFieldInput(
            hint = stringResource(id = country),
            countryParam,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_pin_drop),
            onValueChange = countryParamChange,
            KeyboardType.Text,
            ImeAction.Done
        )
    }
}