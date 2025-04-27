package eu.acolombo.work.calendar.design.illustrations

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

object Illustrations {
    const val STROKE_WIDTH = 2f

    val Colors
        @Composable
        get() = MaterialTheme.colorScheme.defaultIllustrationColors

    private val ColorScheme.defaultIllustrationColors
        get() = IllustrationColors(
            fill = secondaryContainer,
            stroke = onSecondaryContainer,
        )
}
