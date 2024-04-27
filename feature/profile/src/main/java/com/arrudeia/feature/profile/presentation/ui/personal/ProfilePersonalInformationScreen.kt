package com.arrudeia.feature.profile.presentation.ui.personal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.component.camera.ImageSelectionScreen
import com.arrudeia.feature.profile.presentation.viewmodel.ProfilePersonalInformationViewModel

@Composable
fun profilePersonalInformationRoute(
    onBackClick: () -> Unit,
    viewModel: ProfilePersonalInformationViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    viewModel.getUserPersonalInformation()

    var showDialogChangePhoto by rememberSaveable { mutableStateOf(false) }
    var nameValue by rememberSaveable { mutableStateOf("") }
    var docIdValue by rememberSaveable { mutableStateOf("") }
    var emailValue by rememberSaveable { mutableStateOf("") }
    var phoneValue by rememberSaveable { mutableStateOf("") }
    var birthDateValue by rememberSaveable { mutableStateOf("") }
    var profileImageValue by rememberSaveable { mutableStateOf("") }
    var showForm by rememberSaveable { mutableStateOf(false) }

    if (showDialogChangePhoto)
        ImageSelectionScreen(
            { viewModel.onTakePhoto(it) },
            { showDialogChangePhoto = it }
        )
    else
        screenView(
            viewModel,
            { showDialogChangePhoto = it },
            nameValue,
            { nameValue = it },
            docIdValue,
            { docIdValue = it },
            emailValue,
            { emailValue = it },
            phoneValue,
            { phoneValue = it },
            birthDateValue,
            { birthDateValue = it },
            showForm,
            { showForm = it },
            viewModel.uri.value ?: profileImageValue,
            { profileImageValue = it },
            onShowSnackbar,
            onBackClick
        )

}
@Suppress("LongMethod")
@Composable
fun screenView(
    viewModel: ProfilePersonalInformationViewModel,
    showDialogChangePhotoChange: (Boolean) -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    docId: String,
    onDocIdChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    birthDate: String,
    onBirthDateChange: (String) -> Unit,
    showForm: Boolean,
    onShowFormChange: (Boolean) -> Unit,
    profileImage: Any,
    onProfileImageChange: (String) -> Unit,
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
                iconSize = 50.dp
            )
        }

        formBehaviour(
            showForm,
            profileImage,
            showDialogChangePhotoChange,
            name,
            onNameChange,
            docId,
            onDocIdChange,
            email,
            onEmailChange,
            phone,
            onPhoneChange,
            birthDate,
            onBirthDateChange,
            viewModel,
            onShowFormChange,
            onProfileImageChange,
            onShowSnackbar,
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
        var colorButton: Color
        var clickButton: () -> Unit
        if (updatingUser) {
            updateUser(
                viewModel = viewModel,
                name,
                email,
                phone,
                docId,
                birthDate,
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
        updatePersonalButton(
            clickButton, colorButton, Modifier.Companion
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
        )

    }
}