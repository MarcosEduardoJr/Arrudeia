package com.arrudeia.feature.profile.presentation.ui.address

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAddressViewModel


@Composable
fun formBehaviour(
    showForm: Boolean,
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
    viewModel: ProfileAddressViewModel,
    onShowFormChange: (Boolean) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.size(30.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            title(modifier = Modifier.align(Alignment.Center))
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

