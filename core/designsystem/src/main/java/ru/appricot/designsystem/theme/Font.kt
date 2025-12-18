package ru.appricot.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

val LocalTypography =
    staticCompositionLocalOf<Typography> { error("No default typography found") }

@Composable
fun getTypography(): Typography {
    val defaultLineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    )
    val defaultFontFamily = FontFamily.SansSerif

    return Typography(
        displayLarge = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 38.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        displayMedium = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 34.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        displaySmall = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 30.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        headlineLarge = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 28.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        headlineMedium = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 26.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        headlineSmall = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 24.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        titleLarge = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 24.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        titleMedium = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 22.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        titleSmall = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 20.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        bodyLarge = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 22.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        bodyMedium = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 20.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        bodySmall = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 18.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        labelLarge = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 20.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        labelMedium = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 18.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
        labelSmall = TextStyle(
            fontFamily = defaultFontFamily,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 16.sp,
            lineHeightStyle = defaultLineHeightStyle,
        ),
    )
}
