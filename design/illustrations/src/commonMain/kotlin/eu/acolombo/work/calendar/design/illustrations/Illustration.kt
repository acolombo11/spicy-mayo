package eu.acolombo.work.calendar.design.illustrations

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Illustration {
    val image: ImageVector
        @Composable
        get() = getImage(
            colors = IllustrationDefaults.illustrationColors(),
        )

    abstract fun getImage(
        colors: IllustrationColors,
        strokeWidth: Float = IllustrationDefaults.IllustrationStrokeWidth,
    ): ImageVector
}

data class IllustrationColors(
    val fill: Color,
    val stroke: Color,
) {
    val solidFill = SolidColor(fill)
    val solidStroke = SolidColor(stroke)
}

object IllustrationDefaults {

    const val IllustrationStrokeWidth = 2f

    @Composable
    fun illustrationColors() = MaterialTheme.colorScheme.defaultIllustrationColors

    @Composable
    fun illustrationColors(
        fill: Color = Color.Unspecified,
        stroke: Color = Color.Unspecified,
    ): IllustrationColors = MaterialTheme.colorScheme.defaultIllustrationColors.copy(
        fill = fill,
        stroke = stroke,
    )

    private val ColorScheme.defaultIllustrationColors
        get() = IllustrationColors(
            fill = secondaryContainer,
            stroke = onSecondaryContainer,
        )
}