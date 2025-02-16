package com.arrudeia.feature.profile.presentation.ui.personal

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.arrudeia.core.common.R.string.save

@Composable
fun updatePersonalButton(
    clickButton: () -> Unit,
    colorButton: Color,
    modifier: Modifier
) {
        Text(
            text = stringResource(id =  save),
            color = colorButton,
            modifier = modifier.clickable { clickButton()  },
            textAlign = TextAlign.Center
        )
}

