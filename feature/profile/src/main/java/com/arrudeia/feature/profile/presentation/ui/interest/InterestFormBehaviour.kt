package com.arrudeia.feature.profile.presentation.ui.interest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.common.R.string.about_me
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAboutMeViewModel

@Composable
fun InterestFormBehaviour(
    showForm: Boolean,
    interestParam: String,
    interestParamChange: (String) -> Unit,
    biographyParam: String,
    biographyParamChange: (String) -> Unit,
    viewModel: ProfileAboutMeViewModel,
    onShowFormChange: (Boolean) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier
) {
    var previousInterests by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier
    ) {

        if (showForm)
            InterestForm(
             interestParamChange=   interestParamChange,
            biographyParam =     biographyParam,
            biographyParamChange=    biographyParamChange,
            interestParam =    interestParam,
           previousInterests =     previousInterests
            )
        else {
            InterestFetchUser(
                viewModel,
                interestParamChange,
                biographyParamChange,
                onShowFormChange,
                onShowSnackbar
            ) { previousInterests = it }
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
            text = stringResource(about_me),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    }
}

