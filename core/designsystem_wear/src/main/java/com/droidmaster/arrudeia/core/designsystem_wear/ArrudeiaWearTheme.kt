package com.droidmaster.arrudeia.core.designsystem_wear


import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material3.ColorScheme
import androidx.wear.compose.material3.MaterialTheme

private fun createColorScheme(): ColorScheme {
    val phoneTheme = CustomColorScheme
    return ColorScheme(
        primary = phoneTheme.primary,
        primaryContainer = phoneTheme.primaryContainer,
        onPrimary = phoneTheme.onPrimary,
        onPrimaryContainer = phoneTheme.onPrimaryContainer,
        secondary = phoneTheme.secondary,
        onSecondary = phoneTheme.onSecondary,
        secondaryContainer = phoneTheme.secondaryContainer,
        onSecondaryContainer = phoneTheme.onSecondaryContainer,
        tertiary = phoneTheme.tertiary,
        onTertiary = phoneTheme.onTertiary,
        tertiaryContainer = phoneTheme.tertiaryContainer,
        onTertiaryContainer = phoneTheme.onTertiaryContainer,
        onSurface = phoneTheme.onSurface,
        onSurfaceVariant = phoneTheme.onSurfaceVariant,
        background = phoneTheme.background,
        error = phoneTheme.error,
        onError = phoneTheme.onError,
        onBackground = phoneTheme.onBackground,
    )
}


//private val WearColors = createColorScheme()

@Composable
fun ArrudeiaWearTheme(content: @Composable () -> Unit) {
    MaterialTheme(
      //  colorScheme = WearColors,
    ) {
        content()
    }
}

val colorPrimary = Color(0xFFFF6421)
val colorPrimaryBase = Color(0xFFEE684A)
val colorPrimaryDark = Color(0xFF93531A)
val colorPrimaryLight = Color(0xFFFF7029)
val colorWhite = Color(0xFFFFFFFF)
val colorBlack = Color(0xFF000000)
val backgroundGreyF7F7F9 = Color(0xFFF7F7F9)
val textGrey = Color(0xFF949494)
val backgroundGreen008000 = Color(0xFF008000)
val backgroundRedFF0000 = Color(0xFFFF0000)

val CustomColorScheme = darkColorScheme(
    primary = colorPrimary,
    onPrimary = colorWhite,
    primaryContainer = colorPrimaryBase,
    secondary = backgroundGreen008000,
    onSecondary = colorWhite,
    tertiary = backgroundRedFF0000,
    onTertiary = colorWhite,
    background = backgroundGreyF7F7F9,
    onBackground = textGrey,
    surface = colorPrimaryDark,
    onSurface = colorWhite,
    error = colorPrimaryLight,
    onError = colorBlack
)
