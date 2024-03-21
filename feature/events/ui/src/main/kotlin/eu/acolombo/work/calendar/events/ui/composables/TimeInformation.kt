package eu.acolombo.work.calendar.events.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.acolombo.work.calendar.events.domain.model.Offices
import eu.acolombo.work.calendar.events.domain.model.Update
import eu.acolombo.work.calendar.events.ui.R
import eu.acolombo.work.calendar.design.theme.Spacing
import kotlinx.datetime.LocalTime

@Composable
fun TimeInformation(
    modifier: Modifier,
    update: Update?,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(.6f)
                .padding(Spacing.M),
        ) {
            Text(
                text = update?.latest.toTimeString(),
                style = MaterialTheme.typography.displayLarge,
            )
            Text(
                text = stringResource(id = R.string.title_last_update),
                style = MaterialTheme.typography.labelLarge,
            )

        }
        VerticalDivider(
            modifier = Modifier
                .padding(vertical = Spacing.XXL)
                .width(1.5.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(.4f)
                .padding(Spacing.XL),
        ) {
            Offices.entries.forEach {
                Text(
                    text = update?.zones?.get(it).toTimeString(),
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.height(Spacing.S))
            }
        }
    }
}

private fun LocalTime?.toTimeString(
    maxLength: Int = 5,
    replacement: String = "--:--",
): String = this?.toString()?.substring(0, maxLength) ?: replacement