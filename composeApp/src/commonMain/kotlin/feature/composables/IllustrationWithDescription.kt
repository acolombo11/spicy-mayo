package feature.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import illustrations.Illustration
import theme.Spacing

@Composable
fun IllustrationWithDescription(
    illustration: Illustration,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(.65f),
            imageVector = illustration.image,
            contentDescription = illustration.image.name,
        )
        Text(
            modifier = Modifier
                .padding(Spacing.M)
                .fillMaxSize(),
            text = description,
            textAlign = TextAlign.Center,
        )
    }
}
