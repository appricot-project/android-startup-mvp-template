package ru.appricot.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val dayColorPalette = AppColors(
    primaryColor = Color(0xFF1E3A8A),
    primaryColorText = Color(0xFF0B1220),
    secondaryColorText = Color.White,
    inactiveColorText = Color(0xFF9CA3AF),
    primaryBackground = Color(0xFFF9FBFF), // content background
    secondaryBackground = Color(0xFFE5E7EB), // bars / surfaces for contrast
    primaryDivider = Color(0xFFD7DDE6),
    secondaryDivider = Color(0xFFC0C6D0),
    selectedTab = Color(0xFF0EA5E9),
    textButtonColor = Color(0xFF2563EB),
    alertOkColor = Color(0xFF16A34A),
    errorColor = Color(0xFFEF4444),
    accentBackground = Color(0xFFE8F0FF),
    lightBlack = Color(0x80000000),
    sliderGray = Color(0xFFD8DEE4),
    sliderDarkGray = Color(0xFF4B5563),
    sliderGrayAccent = Color.Black,
    border = Color(0xFFCBD5E1),
    slider = Color(0xFF94A3B8).copy(alpha = 0.5f),
    requestField = Color(0xFFEFF4FB),
    primaryBlue = Color(0xFF2563EB),
    slateBlue = Color(0xFF0EA5E9),
    inactiveColorBackground = Color(0xFFD9DDE3),
)

val nightColorPalette = AppColors(
    primaryColor = Color(0xFF9CC7FF),
    primaryColorText = Color(0xFFF8FAFC),
    secondaryColorText = Color(0xFF050915),
    inactiveColorText = Color(0xFF9CA3B8),
    primaryBackground = Color(0xFF0A0F1A),
    secondaryBackground = Color(0xFF161E2D),
    primaryDivider = Color(0xFF1E2736),
    secondaryDivider = Color(0xFF2A3545),
    selectedTab = Color(0xFF38BDF8),
    textButtonColor = Color(0xFF9CC7FF),
    alertOkColor = Color(0xFF22C55E),
    errorColor = Color(0xFFF87171),
    accentBackground = Color(0xFF0F2137),
    lightBlack = Color(0x80000000),
    sliderGray = Color(0xFF4B5563),
    sliderDarkGray = Color(0xFF9CA3AF),
    sliderGrayAccent = Color.White,
    border = Color(0xFF1F2937),
    slider = Color(0xFF1E293B).copy(alpha = 0.6f),
    requestField = Color(0xFF0B1220),
    primaryBlue = Color(0xFF9CC7FF),
    slateBlue = Color(0xFF3B82F6),
    inactiveColorBackground = Color(0xFF1F2937),
)
val LocalColors =
    staticCompositionLocalOf<AppColors> { error("No default implementation for colors") }
internal val LocalThemeIsDark = staticCompositionLocalOf { mutableStateOf(true) }

object HubAppTheme {
    val colors: AppColors
        @Composable
        get() = LocalColors.current
}

@Composable
fun m3ColorScheme(darkTheme: Boolean = isSystemInDarkTheme()): ColorScheme {
    val colors = if (darkTheme) nightColorPalette else dayColorPalette
    return ColorScheme(
        primary = colors.primaryBlue,
        onPrimary = colors.secondaryColorText,
        primaryContainer = colors.accentBackground,
        onPrimaryContainer = colors.primaryColorText,
        inversePrimary = colors.primaryBlue,

        secondary = colors.slateBlue,
        onSecondary = colors.secondaryColorText,
        secondaryContainer = colors.secondaryBackground,
        onSecondaryContainer = colors.primaryColorText,

        tertiary = colors.alertOkColor,
        onTertiary = colors.secondaryColorText,
        tertiaryContainer = colors.requestField,
        onTertiaryContainer = colors.primaryColorText,

        background = colors.primaryBackground,
        onBackground = colors.primaryColorText,
        surface = colors.secondaryBackground,
        onSurface = colors.primaryColorText,
        surfaceVariant = colors.accentBackground,
        onSurfaceVariant = colors.primaryColorText,
        surfaceTint = colors.primaryBlue,
        inverseSurface = colors.primaryBackground,
        inverseOnSurface = colors.primaryColorText,

        error = colors.errorColor,
        onError = colors.secondaryColorText,
        errorContainer = colors.errorColor.copy(alpha = 0.18f),
        onErrorContainer = colors.primaryColorText,

        outline = colors.primaryDivider,
        outlineVariant = colors.secondaryDivider,
        scrim = colors.lightBlack,

        surfaceBright = colors.secondaryBackground,
        surfaceDim = colors.primaryBackground,
        surfaceContainer = colors.secondaryBackground,
        surfaceContainerHigh = colors.secondaryBackground,
        surfaceContainerHighest = colors.secondaryBackground,
        surfaceContainerLow = colors.accentBackground,
        surfaceContainerLowest = colors.accentBackground,

        primaryFixed = colors.primaryBlue,
        primaryFixedDim = colors.primaryColor,
        onPrimaryFixed = colors.secondaryColorText,
        onPrimaryFixedVariant = colors.primaryColorText,

        secondaryFixed = colors.slateBlue,
        secondaryFixedDim = colors.primaryColor,
        onSecondaryFixed = colors.secondaryColorText,
        onSecondaryFixedVariant = colors.primaryColorText,

        tertiaryFixed = colors.alertOkColor,
        tertiaryFixedDim = colors.textButtonColor,
        onTertiaryFixed = colors.secondaryColorText,
        onTertiaryFixedVariant = colors.primaryColorText,
    )
}
