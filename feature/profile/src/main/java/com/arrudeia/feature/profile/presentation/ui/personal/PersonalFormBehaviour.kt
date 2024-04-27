package com.arrudeia.feature.profile.presentation.ui.personal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arrudeia.feature.profile.presentation.viewmodel.ProfilePersonalInformationViewModel

@Composable
fun formBehaviour(
    showForm: Boolean,
    profileImage: Any,
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
    viewModel: ProfilePersonalInformationViewModel,
    onShowFormChange: (Boolean) -> Unit,
    onProfileImageChange: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier
) {
    Column(
        modifier = modifier
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
            if (showForm)
                profileShorDetail(
                    Modifier
                        .align(Alignment.Center),
                    profileImage,
                    showDialogChangePhoto = showDialogChangePhotoChange
                )
        }
        Spacer(modifier = Modifier.size(40.dp))
        if (showForm)
            form(
                name, onNameChange,
                docId, onDocIdChange,
                email, onEmailChange,
                phone, onPhoneChange,
                birthDate, onBirthDateChange,
            )
        else {
            fetchUser(
                viewModel,
                onNameChange,
                onEmailChange,
                onPhoneChange,
                onDocIdChange,
                onBirthDateChange,
                onShowFormChange,
                onProfileImageChange,
                onShowSnackbar
            )
        }
    }
}