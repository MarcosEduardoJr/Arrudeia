package com.arrudeia.feature.profile.presentation.ui.interest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.arrudeia.core.common.R.string.interests
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.feature.profile.presentation.ui.profile.ProfileHeaderBackButton
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAboutMeViewModel

@Composable
fun ProfileInterestRoute(
    onBackClick: () -> Unit,
    viewModel: ProfileAboutMeViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    viewModel.getUserInterests()

    var interests by rememberSaveable { mutableStateOf("") }
    var biography by rememberSaveable { mutableStateOf("") }
    var showForm by rememberSaveable { mutableStateOf(false) }

    InterestScreenView(
        viewModel,
        interests,
        {
            interests = it
        },
        biography,
        { biography = it },
        showForm,
        { showForm = it },
        onShowSnackbar,
        onBackClick
    )
}

@Suppress("LongMethod")
@Composable
fun InterestScreenView(
    viewModel: ProfileAboutMeViewModel,
    interestParam: String,
    interestParamChange: (String) -> Unit,
    biographyParam: String,
    biographyParamChange: (String) -> Unit,
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
        ) {

            ProfileHeaderBackButton(interests, onBackClick)

            InterestFormBehaviour(
                showForm,
                interestParam,
                interestParamChange,
                biographyParam,
                biographyParamChange,
                viewModel,
                onShowFormChange,
                onShowSnackbar,
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )


        }
        var colorButton: Color
        var clickButton: () -> Unit

        if (updatingUser) {
            InterestUpdateUser(
                viewModel = viewModel,
                interestParam,
                biographyParam,
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
        InterestUpdateButton(
            clickButton, colorButton, Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp,end =16.dp)
        )

    }
}
