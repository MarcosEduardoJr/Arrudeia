package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.auth.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.ui.theme.spacing

@Composable
fun TextLightweight() {
    Text(
        modifier = Modifier.padding(top = MaterialTheme.spacing.large),
        text = "Lightweight instant messaging",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.outline
    )
}