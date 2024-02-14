package com.arrudeia.core.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

/**
 * Light default theme color scheme
 */
@VisibleForTesting
val LightDefaultColorScheme = lightColorScheme(
    primary = PrimaryColor ,
    onPrimary = PrimaryColor,
    primaryContainer = PrimaryColor,
    onPrimaryContainer = PrimaryColor,
    secondary = PrimaryColor,
    onSecondary = Color.White,
    secondaryContainer = PrimaryColor,
    onSecondaryContainer = PrimaryColor,
    tertiary = PrimaryColor,
    onTertiary = Color.White,
    tertiaryContainer = PrimaryColor,
    onTertiaryContainer = PrimaryColor,
    error = PrimaryColor,
    onError = Color.White,
    errorContainer = PrimaryColor,
    onErrorContainer = PrimaryColor,
    background = PrimaryColor,
    onBackground = PrimaryColor,
    surface = PrimaryColor,
    onSurface = PrimaryColor,
    surfaceVariant = PrimaryColor,
    onSurfaceVariant = PrimaryColor,
    inverseSurface = PrimaryColor,
    inverseOnSurface = PrimaryColor,
    outline = PrimaryColor,
)

/**
 * Light Android gradient colors
 */
val LightAndroidGradientColors = GradientColors(container = PrimaryColor)

/**
 * Light Android background theme
 */
val LightAndroidBackgroundTheme = BackgroundTheme(color = PrimaryColor)


/**
 *  theme.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 * @param androidTheme Whether the theme should use the Android theme color scheme instead of the
 *        default theme.
 * @param disableDynamicTheming If `true`, disables the use of dynamic theming, even when it is
 *        supported. This parameter has no effect if [androidTheme] is `true`.
 */
@Composable
fun ArrudeiaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    // Color scheme
    val colorScheme = LightDefaultColorScheme

    val gradientColors = LightAndroidGradientColors

    val backgroundTheme = LightAndroidBackgroundTheme
    val tintTheme = TintTheme(colorScheme.primary)
    // Composition locals
    CompositionLocalProvider(
        LocalGradientColors provides gradientColors,
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides tintTheme,
    ) {
        val view = LocalView.current
        val window = (view.context as Activity).window
        window.statusBarColor = Color.White.toArgb() // change color status bar here

        MaterialTheme(
            colorScheme = colorScheme,
            typography = ArrudeiaTypography,
            content = content,
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
