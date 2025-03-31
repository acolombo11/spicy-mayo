package eu.acolombo.work.calendar.design.illustrations

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor

data class IllustrationColors(
    val fill: Color,
    val stroke: Color,
) {
    val solidFill = SolidColor(fill)
    val solidStroke = SolidColor(stroke)
}
