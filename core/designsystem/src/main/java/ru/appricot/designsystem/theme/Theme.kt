package ru.appricot.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

internal val LocalThemeIsDark = staticCompositionLocalOf { mutableStateOf(true) }

@Composable
internal fun AppTheme(content: @Composable () -> Unit) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember { mutableStateOf(systemIsDark) }
    val fonts = getTypography()
    var customAppLocale by remember { mutableStateOf<String?>("ru") }
    val colors = if (systemIsDark) nightColorPalette else dayColorPalette

    CompositionLocalProvider(
        LocalAppLocale provides customAppLocale,
        LocalThemeIsDark provides isDarkState,
        LocalColors provides colors,
        LocalTypography provides fonts,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(HubAppTheme.colors.primaryBackground)
            ) {
                content.invoke()
            }
        }
    )
}
