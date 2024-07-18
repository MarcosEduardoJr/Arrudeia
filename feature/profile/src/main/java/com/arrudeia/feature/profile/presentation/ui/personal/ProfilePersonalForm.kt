package com.arrudeia.feature.profile.presentation.ui.personal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R.drawable.ic_calendar_event
import com.arrudeia.core.designsystem.R.drawable.ic_document
import com.arrudeia.core.designsystem.R.drawable.ic_email
import com.arrudeia.core.designsystem.R.drawable.ic_person
import com.arrudeia.core.designsystem.R.drawable.ic_smartphone
import com.arrudeia.core.designsystem.R.drawable.ic_smile_photo
import com.arrudeia.core.designsystem.component.RectangleCircleBorderItemButton
import com.arrudeia.core.designsystem.component.TextFieldInput
import com.arrudeia.core.ui.document.DocumentAnalisys
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.R.string.cpf

@Composable
fun form(
    name: String, onNameChange: (String) -> Unit,
    docId: String, onDocIdChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    phone: String, onPhoneChange: (String) -> Unit,
    birthDate: String, onBirthDateChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    showDocumentAnalysisChange: (Boolean) -> Unit,
    showDocumentAnalysis: Boolean,
) {
    Column(Modifier.padding(horizontal = 16.dp)) {


        RectangleCircleBorderItemButton(
            iconStart = ic_document,
            name = stringResource(id = R.string.identification_document),
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    showDocumentAnalysisChange(!showDocumentAnalysis)
                },
            iconEnd = ic_smile_photo,
            description = stringResource(id = R.string.identification_document_to_use_services)
        )

        Spacer(modifier = Modifier.size(10.dp))
        TextFieldInput(
            hint = stringResource(id = R.string.full_name),
            name,
            icon = painterResource(id = ic_person),
            onValueChange = onNameChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.cpf),
            docId,
            icon = painterResource(id = ic_document),
            onValueChange = onDocIdChange,
            KeyboardType.Phone,
            ImeAction.Next,
            mask = "###.###.###-##"
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.email),
            email,
            icon = painterResource(id = ic_email),
            onValueChange = onEmailChange,
            KeyboardType.Email,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.phone),
            phone,
            icon = painterResource(id = ic_smartphone),
            onValueChange = onPhoneChange,
            KeyboardType.Phone,
            ImeAction.Next,
            mask = "(##) #####-####"
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.birth_date),
            birthDate,
            icon = painterResource(id = ic_calendar_event),
            onValueChange = onBirthDateChange,
            KeyboardType.Number,
            ImeAction.Done,
            mask = "##/##/####"
        )

    }
}