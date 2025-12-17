package ru.appricot.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

val LocalTypography =
    staticCompositionLocalOf<Typography> { error("No default typography found") }

@Composable
fun getTypography(): Typography {
    val defaultLineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )
    return Typography(
        displayLarge = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 24.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        displayMedium = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 22.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        displaySmall = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 22.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        headlineLarge = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 24.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        headlineMedium = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 22.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        headlineSmall = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 20.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        titleLarge = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 22.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        titleMedium = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 18.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        titleSmall = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 16.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        bodyLarge = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 20.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        bodyMedium = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 18.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        bodySmall = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 16.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        labelLarge = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 18.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        labelMedium = TextStyle(
            fontSize = 12.sp,

            fontWeight = FontWeight.W400,
            lineHeight = 16.sp,
            lineHeightStyle = defaultLineHeightStyle
        ),
        labelSmall = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 14.sp,
            lineHeightStyle = defaultLineHeightStyle
        )
    )
}
