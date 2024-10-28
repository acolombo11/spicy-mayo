package eu.acolombo.work.calendar.events.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import eu.acolombo.work.calendar.design.illustrations.Illustration
import eu.acolombo.work.calendar.design.theme.Spacing

@Composable
fun IllustrationWithDescription(
    illustration: Illustration,
    description: String,
    modifier: Modifier = Modifier,
    button: String? = null,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(.65f),
            imageVector = illustration.image,
            contentDescription = illustration.image.name,
        )
        Text(
            modifier = Modifier.padding(Spacing.M),
            text = description,
            textAlign = TextAlign.Center,
        )
        button?.let {
            Button(onClick) {
                Text(text = it)
            }
        }
    }
}
