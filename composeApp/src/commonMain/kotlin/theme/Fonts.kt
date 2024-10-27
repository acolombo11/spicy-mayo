package theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import spicy_mayo_multiplatform.composeapp.generated.resources.Res
import spicy_mayo_multiplatform.composeapp.generated.resources.space_grotesk_bold
import spicy_mayo_multiplatform.composeapp.generated.resources.space_grotesk_medium
import spicy_mayo_multiplatform.composeapp.generated.resources.space_grotesk_regular
import spicy_mayo_multiplatform.composeapp.generated.resources.space_grotesk_semi_bold
import spicy_mayo_multiplatform.composeapp.generated.resources.space_mono_bold
import spicy_mayo_multiplatform.composeapp.generated.resources.space_mono_regular
import org.jetbrains.compose.resources.Font


val spaceMonoFamily
    @Composable get() = FontFamily(
        Font(resource = Res.font.space_mono_regular, weight = FontWeight.Normal),
        Font(resource = Res.font.space_mono_bold, weight = FontWeight.Bold),
    )

val spaceGroteskFamily
    @Composable get() = FontFamily(
        Font(resource = Res.font.space_grotesk_regular, weight = FontWeight.Normal),
        Font(resource = Res.font.space_grotesk_medium, weight = FontWeight.Medium),
        Font(resource = Res.font.space_grotesk_semi_bold, weight = FontWeight.SemiBold),
        Font(resource = Res.font.space_grotesk_bold, weight = FontWeight.Bold),
    )
