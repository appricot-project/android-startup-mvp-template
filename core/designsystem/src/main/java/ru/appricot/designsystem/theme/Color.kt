package ru.appricot.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val dayColorPalette = AppColors(
    primaryColor = Color(25, 41, 55),
    primaryColorText = Color(34, 34, 34),
    secondaryColorText = Color.White,
    inactiveColorText = Color(113, 117, 122),
    primaryBackground = Color.White,
    secondaryBackground = Color(242, 242, 242),
    primaryDivider = Color(211, 223, 233),
    secondaryDivider = Color(153, 153, 153),
    selectedTab = Color(0, 181, 238),
    textButtonColor = Color(30, 120, 198),
    alertOkColor = Color(0, 122, 255),
    errorColor = Color(236, 64, 64),
    accentBackground = Color(241, 247, 253),
    lightBlack = Color(0, 0, 0).copy(0.5f),
    sliderGray = Color(204, 204, 204),
    sliderDarkGray = Color(68, 68, 68),
    primaryGray = Color(148, 148, 148),
    sliderGrayAccent = Color.Black,
    border = Color(205, 218, 230),
    slider = Color(191, 191, 191).copy(0.44f),
    requestField = Color(241, 247, 252),
    inactiveColorBackground = Color(113, 117, 122),
    primaryBlue = Color(55, 151, 224),
    focusFieldBlue = Color(30, 120, 198),
    codeFieldBlue = Color(243, 248, 252),
    slateBlue = Color(211, 223, 233),
    loyaltyLevelBlue = Color(59, 77, 94),
    qrCodeColor = Color(25, 41, 55),
    urlCollor = Color(30, 120, 198),
    cashBackColor = Color(213, 217, 220)
)

val nightColorPalette = AppColors(
    primaryColor = Color(247, 250, 252),
    primaryColorText = Color.White,
    secondaryColorText = Color(25, 41, 55),
    inactiveColorText = Color(213, 217, 220),
    primaryBackground = Color(25, 41, 55),
    secondaryBackground = Color(39, 43, 47),
    primaryDivider = Color(60, 60, 60),
    secondaryDivider = Color(68, 70, 74),
    selectedTab = Color(0, 181, 238),
    textButtonColor = Color(30, 120, 198),
    alertOkColor = Color(0, 122, 255),
    errorColor = Color(255, 108, 108),
    accentBackground = Color(31, 48, 64),
    lightBlack = Color(0, 0, 0).copy(0.5f),
    sliderGray = Color(204, 204, 204),
    sliderDarkGray = Color(156, 156, 156),
    primaryGray = Color(148, 148, 148),
    sliderGrayAccent = Color.White,
    border = Color(205, 218, 230),
    slider = Color(37, 37, 37).copy(0.55f),
    requestField = Color(31, 48, 64),
    inactiveColorBackground = Color(113, 117, 122),
    primaryBlue = Color(55, 151, 224),
    focusFieldBlue = Color(58, 140, 212),
    codeFieldBlue = Color(34, 55, 73),
    slateBlue = Color(59, 77, 94),
    loyaltyLevelBlue = Color(59, 77, 94),
    qrCodeColor = Color.White,
    urlCollor = Color(58, 140, 212),
    cashBackColor = Color(213, 217, 220)
)
val LocalColors =
    staticCompositionLocalOf<AppColors> { error("No default implementation for colors") }

object HubAppTheme {
    val colors: AppColors
        @Composable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        get() = LocalTypography.current
}

object HubAppColors {
    val Blue50 = Color(0xFFD3DFE9)
    val Blue80 = Color(0xFF003242)
    val Blue90 = Color(0xFF192937)
    val Teal10 = Color(0xFFC5E8F3)
    val Teal90 = Color(0xFFF3F8FC)
    val Green10 = Color(0xFFDBEDDB)
    val Grey10 = Color(0xFFE3E2E0)
    val Grey20 = Color(213, 217, 220) // #d5d9dc
    val Grey60 = Color(0xFF999999)
    val Grey80 = Color(126, 131, 136)
    val White = Color.White
    val Border = Color(59, 77, 94)
    val NextAfBg = Color(34, 55, 73)
}

@Composable
fun m3ColorScheme(): ColorScheme {
    val colors = if (isSystemInDarkTheme()) nightColorPalette else dayColorPalette
    return ColorScheme(
        primary = colors.primaryBlue,
        onPrimary = colors.primaryColorText,
        primaryContainer = colors.accentBackground,
        onPrimaryContainer = colors.primaryColorText,

        secondary = colors.slateBlue,
        onSecondary = colors.primaryColorText,
        secondaryContainer = colors.secondaryBackground,
        onSecondaryContainer = colors.primaryColorText,

        tertiary = colors.textButtonColor,
        onTertiary = colors.primaryColorText,
        tertiaryContainer = colors.urlCollor,
        onTertiaryContainer = colors.primaryColorText,

        background = colors.primaryBackground,
        onBackground = colors.primaryColorText,

        surface = colors.primaryBackground,
        onSurface = colors.primaryColorText,

        surfaceVariant = colors.accentBackground,
        onSurfaceVariant = colors.primaryColorText,

        error = colors.errorColor,
        onError = colors.primaryColorText,
        errorContainer = colors.errorColor.copy(alpha = 0.15f),
        onErrorContainer = colors.errorColor,

        outline = colors.secondaryDivider,
        outlineVariant = colors.primaryDivider,

        scrim = colors.lightBlack,

        inverseSurface = colors.primaryBackground,
        inverseOnSurface = colors.primaryColorText,
        inversePrimary = colors.primaryBlue,

        surfaceTint = colors.primaryBlue,
        surfaceBright = colors.primaryBackground,
        surfaceDim = colors.primaryBlue,
        surfaceContainer = colors.primaryBackground,
        surfaceContainerHigh = colors.primaryBackground,
        surfaceContainerHighest = colors.primaryBackground,
        surfaceContainerLow = colors.primaryBlue,
        surfaceContainerLowest = colors.primaryBlue
    )
}
