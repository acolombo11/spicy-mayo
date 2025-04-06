package eu.acolombo.work.calendar.design.illustrations

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object Illustrations {

    const val DefaultStrokeWidth = 2f

    val Colors
        @Composable
        get() = MaterialTheme.colorScheme.defaultIllustrationColors

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
