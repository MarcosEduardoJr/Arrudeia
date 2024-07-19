package com.arrudeia.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShortText
import androidx.compose.material.icons.rounded.ViewDay
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 *  view toggle button with included trailing icon as well as compact and expanded
 * text label content slots.
 *
 * @param expanded Whether the view toggle is currently in expanded mode or compact mode.
 * @param onExpandedChange Called when the user clicks the button and toggles the mode.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param compactText The text label content to show in expanded mode.
 * @param expandedText The text label content to show in compact mode.
 */
@Composable
fun ArrudeiaViewToggleButton(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    compactText: @Composable () -> Unit,
    expandedText: @Composable () -> Unit,
) {
    TextButton(
        onClick = { onExpandedChange(!expanded) },
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        contentPadding = ArrudeiaViewToggleDefaults.ViewToggleButtonContentPadding,
    ) {
        ArrudeiaViewToggleButtonContent(
            text = if (expanded) expandedText else compactText,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Rounded.ViewDay else Icons.Rounded.ShortText,
                    contentDescription = null,
                )
            },
        )
    }
}

/**
 * Internal  view toggle button content layout for arranging the text label and
 * trailing icon.
 *
 * @param text The button text label content.
 * @param trailingIcon The button trailing icon content. Default is `null` for no trailing icon.
 */
@Composable
private fun ArrudeiaViewToggleButtonContent(
    text: @Composable () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Box(
        Modifier
            .padding(
                end = if (trailingIcon != null) {
                    ButtonDefaults.IconSpacing
                } else {
                    0.dp
                },
            ),
    ) {
        ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
            text()
        }
    }
    if (trailingIcon != null) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            trailingIcon()
        }
    }
}

/**
 *  view toggle default values.
 */
object ArrudeiaViewToggleDefaults {
    // TODO: File bug
    // Various default button padding values aren't exposed via ButtonDefaults
    val ViewToggleButtonContentPadding =
        PaddingValues(
            start = 16.dp,
            top = 8.dp,
            end = 12.dp,
            bottom = 8.dp,
        )
}
