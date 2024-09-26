package com.arrudeia.feature.profile.presentation.ui.personal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arrudeia.core.common.R.string.birth_date
import com.arrudeia.core.common.R.string.full_name
import com.arrudeia.core.designsystem.R.drawable.ic_calendar_event
import com.arrudeia.core.designsystem.R.drawable.ic_document
import com.arrudeia.core.designsystem.R.drawable.ic_email
import com.arrudeia.core.designsystem.R.drawable.ic_person
import com.arrudeia.core.designsystem.R.drawable.ic_smartphone
import com.arrudeia.core.designsystem.R.drawable.ic_smile_photo
import com.arrudeia.core.designsystem.component.FilterChipIconGroup
import com.arrudeia.core.designsystem.component.RectangleCircleBorderItemButton
import com.arrudeia.core.designsystem.component.TextFieldInput
import com.arrudeia.feature.profile.R
import com.arrudeia.core.profile.GenderOptions
import com.arrudeia.core.common.R.string.cpf as cpfResource
import com.arrudeia.core.common.R.string.email as emailResource
import com.arrudeia.core.common.R.string.phone as phoneResource

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
    genderChooseChange: (String) -> Unit,
    previousGenderChoosed: String
) {
    val context = LocalContext.current
    LazyColumn(Modifier.padding(horizontal = 16.dp)) {

        items(1) {
            RectangleCircleBorderItemButton(
                iconStart = ic_document,
                name = stringResource(id = R.string.identification_document),
                modifier = Modifier
                    .padding(top = 6.dp)
                    .clickable {
                        showDocumentAnalysisChange(!showDocumentAnalysis)
                    },
                iconEnd = ic_smile_photo,
                description = null
            )

            Spacer(modifier = Modifier.size(10.dp))
            TextFieldInput(
                hint = stringResource(id = full_name),
                name,
                icon = painterResource(id = ic_person),
                onValueChange = onNameChange,
                KeyboardType.Text,
                ImeAction.Next
            )
            Spacer(modifier = Modifier.size(4.dp))

            TextFieldInput(
                hint = stringResource(id = cpfResource),
                docId,
                icon = painterResource(id = ic_document),
                onValueChange = onDocIdChange,
                KeyboardType.Phone,
                ImeAction.Next,
                mask = "###.###.###-##"
            )
            Spacer(modifier = Modifier.size(4.dp))

            FilterChipIconGroup(
                title = stringResource(id = com.arrudeia.core.common.R.string.sex),
                chipLabels = GenderOptions.getAllEnumDrawableReferences(context),
                onSelectionChanged = { selectedChips ->
                    selectedChips.map { GenderOptions.getStringFromDrawable(context, it).orEmpty() }
                        .joinToString(separator = ",") { it }
                        .let(genderChooseChange)
                },
                previousSelected = if (previousGenderChoosed.isNotEmpty()) listOf(
                    GenderOptions.getDrawableFromEnum(
                        context,
                        GenderOptions.valueOf(previousGenderChoosed)
                    )
                ) else emptyList(),
                isMultipleChoice = false
            )

            Spacer(modifier = Modifier.size(4.dp))


            TextFieldInput(
                hint = stringResource(id = emailResource),
                email,
                icon = painterResource(id = ic_email),
                onValueChange = onEmailChange,
                KeyboardType.Email,
                ImeAction.Next
            )
            Spacer(modifier = Modifier.size(4.dp))

            TextFieldInput(
                hint = stringResource(id = phoneResource),
                phone,
                icon = painterResource(id = ic_smartphone),
                onValueChange = onPhoneChange,
                KeyboardType.Phone,
                ImeAction.Next,
                mask = "(##) #####-####"
            )
            Spacer(modifier = Modifier.size(4.dp))

            TextFieldInput(
                hint = stringResource(id = birth_date),
                birthDate,
                icon = painterResource(id = ic_calendar_event),
                onValueChange = onBirthDateChange,
                KeyboardType.Number,
                ImeAction.Done,
                mask = "##/##/####"
            )
            Spacer(modifier = Modifier.size(80.dp))
        }
    }
}