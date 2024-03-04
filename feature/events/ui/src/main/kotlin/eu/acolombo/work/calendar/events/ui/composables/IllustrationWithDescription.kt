package eu.acolombo.work.calendar.events.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import eu.acolombo.work.calendar.design.illustrations.Illustration
import eu.acolombo.work.calendar.design.theme.Spacing

@Composable
fun IllustrationWithDescription(
    illustration: Illustration,
    description: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(.65f),
            imageVector = illustration.image,
            contentDescription = illustration.image.name,
        )
        Text(
            text = description,
            modifier = Modifier.padding(Spacing.M),
        )
    }
}
