package com.arrudeia.feature.profile.presentation.ui.personal

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

@Composable
fun form(
    name: String, onNameChange: (String) -> Unit,
    docId: String, onDocIdChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    phone: String, onPhoneChange: (String) -> Unit,
    birthDate: String, onBirthDateChange: (String) -> Unit,
) {
    Column(Modifier.padding(horizontal = 16.dp)) {

        TextFieldInput(
            hint = stringResource(id = R.string.full_name),
            name,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_person),
            onValueChange = onNameChange,
            KeyboardType.Text,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.document_id),
            docId,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_document),
            onValueChange = onDocIdChange,
            KeyboardType.Phone,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.email),
            email,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_email),
            onValueChange = onEmailChange,
            KeyboardType.Email,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.phone),
            phone,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_smartphone),
            onValueChange = onPhoneChange,
            KeyboardType.Phone,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(4.dp))

        TextFieldInput(
            hint = stringResource(id = R.string.birth_date),
            birthDate,
            icon = painterResource(id = com.arrudeia.core.designsystem.R.drawable.ic_calendar_event),
            onValueChange = onBirthDateChange,
            KeyboardType.Number,
            ImeAction.Done
        )

    }
}