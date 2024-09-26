package com.arrudeia.feature.profile.presentation.ui.personal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arrudeia.core.common.R.string.information
import com.arrudeia.feature.profile.presentation.ui.profile.ProfileHeaderBackButton
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
    modifier: Modifier,
    onBackClick: () -> Unit,
    showDocumentAnalysisChange: (Boolean) -> Unit,
    showDocumentAnalysis: Boolean,
    genderChoosedChange: (String) -> Unit
) {
    var previousGenderChoosed by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier
    ) {
        ProfileHeaderBackButton( information, onBackClick)
        Spacer(modifier = Modifier.size(10.dp))
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
        Spacer(modifier = Modifier.size(2.dp))
        if (showForm)
            form(
                name, onNameChange,
                docId, onDocIdChange,
                email, onEmailChange,
                phone, onPhoneChange,
                birthDate, onBirthDateChange,
                onBackClick = onBackClick,
                onShowSnackbar = onShowSnackbar,
                showDocumentAnalysisChange,
                showDocumentAnalysis,
                genderChooseChange = genderChoosedChange,
                previousGenderChoosed = previousGenderChoosed
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
                onShowSnackbar,
                previousGenderChoosed = { previousGenderChoosed = it }
            )
        }
    }
}