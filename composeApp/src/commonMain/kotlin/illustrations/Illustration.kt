package illustrations

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Illustration(
    fill: Color,
    stroke: Color,
) {
    val strokeWidth: Float = 2f
    val fillColor: SolidColor = SolidColor(fill)
    val strokeColor: SolidColor = SolidColor(stroke)
    abstract val image: ImageVector
}
