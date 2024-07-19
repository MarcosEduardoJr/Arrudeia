package com.arrudeia.core.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
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
    primary = BackgroundColorGreyF7F7F9 ,
    onPrimary = BackgroundColorGreyF7F7F9,
    primaryContainer = BackgroundColorGreyF7F7F9,
    onPrimaryContainer = BackgroundColorGreyF7F7F9,
    secondary = BackgroundColorGreyF7F7F9,
    onSecondary = Color.White,
    secondaryContainer = BackgroundColorGreyF7F7F9,
    onSecondaryContainer = BackgroundColorGreyF7F7F9,
    tertiary = BackgroundColorGreyF7F7F9,
    onTertiary = Color.White,
    tertiaryContainer = BackgroundColorGreyF7F7F9,
    onTertiaryContainer = BackgroundColorGreyF7F7F9,
    error = BackgroundColorGreyF7F7F9,
    onError = Color.White,
    errorContainer = BackgroundColorGreyF7F7F9,
    onErrorContainer = BackgroundColorGreyF7F7F9,
    background = BackgroundColorGreyF7F7F9,
    onBackground = BackgroundColorGreyF7F7F9,
    surface = BackgroundColorGreyF7F7F9,
    onSurface = BackgroundColorGreyF7F7F9,
    surfaceVariant = BackgroundColorGreyF7F7F9,
    onSurfaceVariant = BackgroundColorGreyF7F7F9,
    inverseSurface = BackgroundColorGreyF7F7F9,
    inverseOnSurface = BackgroundColorGreyF7F7F9,
    outline = BackgroundColorGreyF7F7F9,
)

/**
 * Light Android gradient colors
 */
val LightAndroidGradientColors = GradientColors(container = BackgroundColorGreyF7F7F9)

/**
 * Light Android background theme
 */
val LightAndroidBackgroundTheme = BackgroundTheme(color = BackgroundColorGreyF7F7F9)



@Composable
public fun ArrudeiaTheme(

    content: @Composable () -> Unit,
) {
    // Color scheme
    val colorScheme = LightDefaultColorScheme

    val gradientColors = LightAndroidGradientColors

    val backgroundTheme = LightAndroidBackgroundTheme
    val tintTheme = TintTheme(BackgroundColorGreyF7F7F9)
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
