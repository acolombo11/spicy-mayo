package eu.acolombo.work.calendar.events.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import eu.acolombo.work.calendar.design.theme.Spacing
import org.jetbrains.compose.resources.stringResource
import spicy_mayo.feature.events.presentation.generated.resources.Res
import spicy_mayo.feature.events.presentation.generated.resources.loading

@Composable
fun IllustrationWithDescription(
    illustration: ImageVector,
    description: String,
    modifier: Modifier = Modifier,
    button: String? = null,
    onClick: () -> Unit = {},
) {
    ContentWithDescription(
        modifier = modifier,
        description = description,
        content = {
            Image(
                modifier = Modifier.fillMaxWidth(),
                imageVector = illustration,
                contentDescription = illustration.name,
            )
        },
        bottomContent = { bottomModifier ->
            button?.let {
                Button(
                    modifier = bottomModifier,
                    onClick = onClick,
                ) {
                    Text(text = it)
                }
            }
        },
    )
}

@Composable
fun LoadingWithDescription(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    description: String = stringResource(Res.string.loading),
) {
    ContentWithDescription(
        modifier = modifier,
        description = description,
        content = { LoadingIndicator(circleColor = color) },
    )
}

@Composable
private fun ContentWithDescription(
    description: String,
    modifier: Modifier = Modifier,
    bottomContent: @Composable BoxScope.(Modifier) -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(ContentWidth)
                .aspectRatio(ContentRatio),
            contentAlignment = Alignment.Center,
        ) {
            content()
            bottomContent(Modifier.align(Alignment.BottomCenter))
        }
        Text(
            modifier = Modifier
                .padding(Spacing.M.dp)
                .fillMaxWidth(),
            text = description,
            textAlign = TextAlign.Center,
        )
    }
}

private const val ContentRatio = .67f
private const val ContentWidth = .65f
