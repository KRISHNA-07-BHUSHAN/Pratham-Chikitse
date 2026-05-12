package com.prathamchikitse.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Emergency Red Palette
val EmergencyRed = Color(0xFFD32F2F)
val EmergencyRedDark = Color(0xFFB71C1C)
val EmergencyRedLight = Color(0xFFEF9A9A)
val SafeGreen = Color(0xFF2E7D32)
val SafeGreenLight = Color(0xFFA5D6A7)
val WarmWhite = Color(0xFFFAFAFA)
val DarkSurface = Color(0xFF1C1C1E)
val CardWhite = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF1A1A1A)
val TextSecondary = Color(0xFF757575)
val AlertOrange = Color(0xFFE65100)
val NeutralGray = Color(0xFFF5F5F5)
val DividerColor = Color(0xFFE0E0E0)

private val LightColorScheme = lightColorScheme(
    primary = EmergencyRed,
    onPrimary = Color.White,
    primaryContainer = EmergencyRedLight,
    onPrimaryContainer = EmergencyRedDark,
    secondary = SafeGreen,
    onSecondary = Color.White,
    secondaryContainer = SafeGreenLight,
    onSecondaryContainer = SafeGreen,
    tertiary = AlertOrange,
    background = WarmWhite,
    onBackground = TextPrimary,
    surface = CardWhite,
    onSurface = TextPrimary,
    surfaceVariant = NeutralGray,
    onSurfaceVariant = TextSecondary,
    outline = DividerColor,
    error = EmergencyRed,
)

private val DarkColorScheme = darkColorScheme(
    primary = EmergencyRedLight,
    onPrimary = EmergencyRedDark,
    primaryContainer = EmergencyRedDark,
    onPrimaryContainer = EmergencyRedLight,
    secondary = SafeGreenLight,
    onSecondary = SafeGreen,
    background = DarkSurface,
    surface = Color(0xFF2C2C2E),
    onSurface = Color.White,
)

@Composable
fun PrathamChikitseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = EmergencyRed.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
