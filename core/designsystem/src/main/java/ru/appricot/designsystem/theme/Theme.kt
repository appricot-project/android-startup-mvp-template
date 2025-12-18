package ru.appricot.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun StartupHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+. Off by default to keep branding.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember { mutableStateOf(systemIsDark) }
    val fonts = getTypography()
    var customAppLocale by remember { mutableStateOf<String?>("ru") }
    val colors = if (darkTheme) nightColorPalette else dayColorPalette

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> m3ColorScheme(darkTheme)
    }

    CompositionLocalProvider(
        LocalAppLocale provides customAppLocale,
        LocalThemeIsDark provides isDarkState,
        LocalColors provides colors,
        LocalTypography provides fonts,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = fonts,
            content = content,
        )
    }
}
