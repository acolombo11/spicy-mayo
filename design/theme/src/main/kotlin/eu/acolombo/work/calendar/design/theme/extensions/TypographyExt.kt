package eu.acolombo.work.calendar.design.theme.extensions

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

private fun Typography.copy(
    displayFontFamily: FontFamily,
    displayFontWeight: FontWeight,
    displayLetterSpacing: TextUnit,
    headlineFontFamily: FontFamily,
    headlineFontWeight: FontWeight,
    headlineLetterSpacing: TextUnit,
    titleFontFamily: FontFamily,
    titleFontWeight: FontWeight,
    titleLetterSpacing: TextUnit,
    bodyFontFamily: FontFamily,
    bodyFontWeight: FontWeight,
    bodyLetterSpacing: TextUnit,
    labelFontFamily: FontFamily,
    labelFontWeight: FontWeight,
    labelLetterSpacing: TextUnit,
) = copy(
    displayLarge = displayLarge.copy(
        fontFamily = displayFontFamily,
        fontWeight = displayFontWeight,
        letterSpacing = displayLetterSpacing,
    ),
    displayMedium = displayMedium.copy(
        fontFamily = displayFontFamily,
        fontWeight = displayFontWeight,
        letterSpacing = displayLetterSpacing,
    ),
    displaySmall = displaySmall.copy(
        fontFamily = displayFontFamily,
        fontWeight = displayFontWeight,
        letterSpacing = displayLetterSpacing,
    ),
    headlineLarge = headlineLarge.copy(
        fontFamily = headlineFontFamily,
        fontWeight = headlineFontWeight,
        letterSpacing = headlineLetterSpacing,
    ),
    headlineMedium = headlineMedium.copy(
        fontFamily = headlineFontFamily,
        fontWeight = headlineFontWeight,
        letterSpacing = headlineLetterSpacing,
    ),
    headlineSmall = headlineSmall.copy(
        fontFamily = headlineFontFamily,
        fontWeight = headlineFontWeight,
        letterSpacing = headlineLetterSpacing,
    ),
    titleLarge = titleLarge.copy(
        fontFamily = titleFontFamily,
        fontWeight = titleFontWeight,
        letterSpacing = titleLetterSpacing,
    ),
    titleMedium = titleMedium.copy(
        fontFamily = titleFontFamily,
        fontWeight = titleFontWeight,
        letterSpacing = titleLetterSpacing,
    ),
    titleSmall = titleSmall.copy(
        fontFamily = titleFontFamily,
        fontWeight = titleFontWeight,
        letterSpacing = titleLetterSpacing,
    ),
    bodyLarge = bodyLarge.copy(
        fontFamily = bodyFontFamily,
        fontWeight = bodyFontWeight,
        letterSpacing = bodyLetterSpacing,
    ),
    bodyMedium = bodyMedium.copy(
        fontFamily = bodyFontFamily,
        fontWeight = bodyFontWeight,
        letterSpacing = bodyLetterSpacing,
    ),
    bodySmall = bodySmall.copy(
        fontFamily = bodyFontFamily,
        fontWeight = bodyFontWeight,
        letterSpacing = bodyLetterSpacing,
    ),
    labelLarge = labelLarge.copy(
        fontFamily = labelFontFamily,
        fontWeight = labelFontWeight,
        letterSpacing = labelLetterSpacing,
    ),
    labelMedium = labelMedium.copy(
        fontFamily = labelFontFamily,
        fontWeight = labelFontWeight,
        letterSpacing = labelLetterSpacing,
    ),
    labelSmall = labelSmall.copy(
        fontFamily = labelFontFamily,
        fontWeight = labelFontWeight,
        letterSpacing = labelLetterSpacing,
    ),
)
