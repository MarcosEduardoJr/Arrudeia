package com.arrudeia.feature.profile.presentation.ui.personal

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.core.common.R.string.save

@Composable
fun updatePersonalButton(
    clickButton: () -> Unit,
    colorButton: Color,
    modifier: Modifier
) {
    ArrudeiaButtonColor(
        onClick = clickButton,
        modifier = modifier,
        colorButton = colorButton,
    ) {
        Text(
            text = stringResource(id =  save),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
        )
    }
}