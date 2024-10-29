package eu.acolombo.work.calendar.events.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.acolombo.work.calendar.design.theme.Spacing
import eu.acolombo.work.calendar.events.model.Office
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import spicy_mayo.feature.events.ui.generated.resources.Res
import spicy_mayo.feature.events.ui.generated.resources.title_last_update
import spicy_mayo.feature.events.ui.generated.resources.label_set_location

@Composable
fun TimeInformation(
    modifier: Modifier,
    latest: Instant?,
    offices: List<Office>,
    onOfficeClick: (index: Int) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(.6f)
                .padding(Spacing.L),
        ) {
            Text(
                text = latest.toTimeString(),
                style = MaterialTheme.typography.displayLarge,
            )
            Text(
                text = stringResource(Res.string.title_last_update),
                style = MaterialTheme.typography.labelLarge,
            )
        }
        VerticalDivider(
            modifier = Modifier
                .padding(vertical = Spacing.L)
                .width(1.5.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(.4f)
                .padding(horizontal = Spacing.M, vertical = Spacing.XL),
        ) {
            offices.take(n = 2, or = { null }).forEachIndexed { index, office ->
                OfficeInformation(
                    latest = latest,
                    office = office,
                    onClick = { onOfficeClick(index) },
                )
            }
        }
    }
}

@Composable
private fun OfficeInformation(
    latest: Instant?,
    office: Office?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(bottom = Spacing.XS)
            .padding(horizontal = Spacing.M, vertical = Spacing.XS),
    ) {
        Text(
            text = latest.toTimeString(timeZone = office?.timezone),
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            text = office?.name ?: stringResource(Res.string.label_set_location),
            style = MaterialTheme.typography.labelLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

private fun <T, R : T> Iterable<T>.take(n: Int, or: (index: Int) -> R): List<T> {
    val taken = take(n)
    return taken + List(n - taken.size) { or(it) }
}

private fun Instant?.toTimeString(
    timeZone: TimeZone? = TimeZone.currentSystemDefault(),
    maxLength: Int = 5,
    replacement: String = "--:--",
): String = timeZone?.let {
    this?.toLocalDateTime(timeZone)
        ?.time
        ?.toString()
        ?.substring(0, maxLength)
} ?: replacement