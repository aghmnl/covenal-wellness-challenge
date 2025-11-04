package com.agus.wellnessapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.agus.wellnessapp.R


val NunitoSans = FontFamily(
    Font(
        resId = R.font.nunito_sans_variable,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_sans_variable,
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_sans_variable,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_sans_variable,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_sans_variable,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_sans_variable,
        weight = FontWeight.Black,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_sans_variable_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.nunito_sans_variable_italic,
        weight = FontWeight.Medium,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.nunito_sans_variable_italic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    )
)

val Typography = Typography(
    headlineMedium = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Black,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    displayLarge = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelLarge = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)