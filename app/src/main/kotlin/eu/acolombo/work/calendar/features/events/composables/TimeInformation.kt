package eu.acolombo.work.calendar.features.events.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.acolombo.work.calendar.R
import eu.acolombo.work.calendar.data.model.Offices
import eu.acolombo.work.calendar.data.model.Update
import eu.acolombo.work.calendar.ui.Spacing

@Composable
fun TimeInformation(
    modifier: Modifier,
    update: Update?,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.M)
        ) {
            val mappedUpdates = Offices.entries.map {
                it.name to update?.zones?.get(it)
            } + (stringResource(id = R.string.title_last_update) to update?.latest)

            mappedUpdates.forEach { (title, time) ->
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                )
                Text(
                    text = time?.toString()?.substring(0..4) ?: "--:--",
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(modifier = Modifier.height(Spacing.S))
            }
        }
    }
}
