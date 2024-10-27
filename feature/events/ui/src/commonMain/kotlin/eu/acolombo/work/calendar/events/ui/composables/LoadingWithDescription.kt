package eu.acolombo.work.calendar.events.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import eu.acolombo.work.calendar.design.theme.Spacing
import org.jetbrains.compose.resources.stringResource
import spicy_mayo.feature.events.ui.generated.resources.Res
import spicy_mayo.feature.events.ui.generated.resources.loading

@Composable
fun LoadingWithDescription(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    description: String = stringResource(Res.string.loading),
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.65f)
                .aspectRatio(0.67f),
            contentAlignment = Alignment.Center,
        ) {
            LoadingIndicator(circleColor = color)
        }
        Text(
            modifier = Modifier
                .padding(Spacing.M)
                .fillMaxSize(),
            text = description,
            textAlign = TextAlign.Center,
        )
    }
}
