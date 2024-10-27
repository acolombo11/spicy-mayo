package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WorkCalendarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // TODO Use
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        shapes = shapes,
        typography = typography,
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
            fontSize = 58.sp,
        ),
        displayMedium = MaterialTheme.typography.displayMedium.copy(
            fontFamily = spaceMonoFamily,
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
