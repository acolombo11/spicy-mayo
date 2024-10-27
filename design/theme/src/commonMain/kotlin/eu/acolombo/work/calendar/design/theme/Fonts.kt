package eu.acolombo.work.calendar.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import spicy_mayo_multiplatform.design.theme.generated.resources.Res
import spicy_mayo_multiplatform.design.theme.generated.resources.space_grotesk_bold
import spicy_mayo_multiplatform.design.theme.generated.resources.space_grotesk_medium
import spicy_mayo_multiplatform.design.theme.generated.resources.space_grotesk_regular
import spicy_mayo_multiplatform.design.theme.generated.resources.space_grotesk_semi_bold
import spicy_mayo_multiplatform.design.theme.generated.resources.space_mono_bold
import spicy_mayo_multiplatform.design.theme.generated.resources.space_mono_regular

val spaceMonoFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.space_mono_regular, FontWeight.Normal),
        Font(Res.font.space_mono_bold, FontWeight.Bold),
    )

val spaceGroteskFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.space_grotesk_regular, FontWeight.Normal),
        Font(Res.font.space_grotesk_medium, FontWeight.Medium),
        Font(Res.font.space_grotesk_semi_bold, FontWeight.SemiBold),
        Font(Res.font.space_grotesk_bold, FontWeight.Bold),
    )
