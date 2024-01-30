package eu.acolombo.work.calendar.ui

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

@Composable
fun WorkCalendarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        shapes = shapes,
        typography = typography,
        colorScheme = colorScheme,
        content = content,
    )
}

private val shapes: Shapes
    @Composable
    get() = MaterialTheme.shapes.copy(
        extraLarge = RoundedCornerShape(36.dp),
    )

private val typography: Typography
    @Composable
    get() = MaterialTheme.typography.copy(
        displayLarge = MaterialTheme.typography.displayLarge.copy(
            fontFamily = spaceMonoFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 72.sp,
        ),
        displayMedium = MaterialTheme.typography.displayMedium.copy(
            fontFamily = spaceMonoFamily,
            fontWeight = FontWeight.Bold,
        ),
        displaySmall = MaterialTheme.typography.displaySmall.copy(
            fontFamily = spaceMonoFamily,
        ),
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(
            fontFamily = spaceMonoFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            lineHeight = 36.sp,
            letterSpacing = -.5.sp,
        ),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(
            fontFamily = spaceMonoFamily,
            fontWeight = FontWeight.Bold,
            lineHeight = 20.sp,
            letterSpacing = -.5.sp,
        ),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(
            fontFamily = spaceMonoFamily,
            fontWeight = FontWeight.Normal,
            letterSpacing = -.5.sp,
        ),
        titleLarge = MaterialTheme.typography.titleLarge.copy(
            fontFamily = spaceMonoFamily,
        ),
        titleMedium = MaterialTheme.typography.titleMedium.copy(
            fontFamily = spaceMonoFamily,
        ),
        titleSmall = MaterialTheme.typography.titleSmall.copy(
            fontFamily = spaceMonoFamily,
        ),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = spaceMonoFamily,
        ),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = spaceMonoFamily,
        ),
        bodySmall = MaterialTheme.typography.bodySmall.copy(
            fontFamily = spaceMonoFamily,
        ),
        labelLarge = MaterialTheme.typography.labelLarge.copy(
            fontFamily = spaceGroteskFamily,
            fontWeight = FontWeight.SemiBold,
        ),
        labelMedium = MaterialTheme.typography.labelMedium.copy(
            fontFamily = spaceGroteskFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
        ),
        labelSmall = MaterialTheme.typography.labelSmall.copy(
            fontFamily = spaceGroteskFamily,
        ),
    )
